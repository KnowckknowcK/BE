package com.knu.KnowcKKnowcK.service.debateRoom;


import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.dto.requestdto.MessageRequestDto;
import com.knu.KnowcKKnowcK.dto.requestdto.MessageThreadRequestDto;
import com.knu.KnowcKKnowcK.dto.requestdto.PreferenceRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageThreadResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.PreferenceResponseDto;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.*;
import com.knu.KnowcKKnowcK.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.knu.KnowcKKnowcK.service.debateRoom.DebateRoomUtil.*;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final DebateRoomRepository debateRoomRepository;
    private final MessageRepository messageRepository;
    private final MessageThreadRepository messageThreadRepository;
    private final PreferenceRepository preferenceRepository;
    private final MemberDebateRepository memberDebateRepository;
    private final RedisUtil redisUtil;

    public MessageResponseDto saveAndReturnMessage(Member member, MessageRequestDto dto){
        DebateRoom debateRoom = debateRoomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        MemberDebate memberDebate = memberDebateRepository.findByMemberAndDebateRoom(member, debateRoom)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));

        Message message = dto.toMessage(member, debateRoom);
        messageRepository.save(message);

        MessageResponseDto responseDto =
                message.toMessageResponseDto(
                        memberDebate.getPosition().name(),
                        0, 0,
                        member.getProfileImage());

        // redis 캐시에 messageResponseDto를 리스트로 저장 -> 조회 속도 증가
        redisUtil.addDataToList(getDebateRoomKey(debateRoom.getId()), responseDto);
        return responseDto;
    }
    public MessageThreadResponseDto saveAndReturnMessageThread(Member member, Long messageId, MessageThreadRequestDto dto) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        DebateRoom debateRoom = debateRoomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        MemberDebate memberDebate = memberDebateRepository.findByMemberAndDebateRoom(member, debateRoom)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));

        MessageThread messageThread = dto.toMessageThread(member, message);
        messageThreadRepository.save(messageThread);

        MessageThreadResponseDto responseDto = messageThread.toMessageThreadResponseDto(messageId,
                memberDebate.getPosition().name(),
                member.getProfileImage());
        // redis 캐시에 messageThreadResponseDto를 리스트로 저장 -> 조회 속도 증가
        redisUtil.addDataToList(getMessageThreadKey(messageId), responseDto);
        return responseDto;
    }

    public List<MessageResponseDto> getMessages(Member member, Long roomId){
        DebateRoom debateRoom = debateRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));

        // redis에서 관련 채팅방 정보가 있는 지 먼저 탐색
        List<MessageResponseDto> messageResponseDtoList =
                redisUtil.getDataList(getDebateRoomKey(debateRoom.getId()), MessageResponseDto.class);

        // 없으면 값을 가져와서 캐시에 저장한 후 반환
        if (messageResponseDtoList.isEmpty()){
            List<Message> messageList = messageRepository.findByDebateRoom(debateRoom);
            MemberDebate memberDebate = memberDebateRepository.findByMemberAndDebateRoom(member, debateRoom)
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
            for (Message message: messageList) {
                messageResponseDtoList.add(message
                        .toMessageResponseDto(memberDebate.getPosition().name(),
                                getPreferenceNum(message),
                                getMessageThreadNum(message) ,
                                message.getMember().getProfileImage()
                        ));
            }
        }
        // 있으면 그 값을 반환
        return messageResponseDtoList;
    }

    public List<MessageThreadResponseDto> getMessageThreadDtoList(Long messageId){
        // redis에서 관련 채팅방 정보가 있는 지 먼저 탐색
        List<MessageThreadResponseDto> messageThreadResponseDtoList =
                redisUtil.getDataList(getMessageThreadKey(messageId), MessageThreadResponseDto.class);

        // 없으면 값을 가져와서 캐시에 저장한 후 반환
        if(messageThreadResponseDtoList.isEmpty()){
            Message message = messageRepository.findById(messageId)
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
            List<MessageThread> messageThraedList = messageThreadRepository.findByMessage(message);
            MemberDebate memberDebate = memberDebateRepository
                    .findByMemberAndDebateRoom(message.getMember(), message.getDebateRoom())
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
            for (MessageThread messageThread: messageThraedList) {
                messageThreadResponseDtoList
                        .add(messageThread.toMessageThreadResponseDto(messageId,
                                memberDebate.getPosition().name(),
                                message.getMember().getProfileImage()));
            }
        }

        // 있으면 그 값을 반환
        return messageThreadResponseDtoList;
    }

    public PreferenceResponseDto putPreference(Member member, Long messageId, PreferenceRequestDto dto){
        dto.validate();
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        // 메세지가 찬성인지 반대인지 구분하는 로직
        // 1. 메세지로부터 그 메세지의 Position을 찾아 내는 방법 -> 암격, 성늘 저하
        // 2. 클라이언트로부터 Position을 입력 바디로 받는 방법 -> 빠름, 요청 조작 시 오류 발생 가능성 존재
        // -> 현재는 2번 선택(추후 오류 발생이 잦다면 1번 사용할 예정)
        boolean isIncrease = updatePreference(member, message, dto);
        // 토론방 내부 ratio 변경 필요
        DebateRoom debateRoom = changePreferenceRatio(
                dto.getIsAgree(),
                isIncrease,
                message.getDebateRoom());

        return makeDto(
                calculateRatio(debateRoom.getAgreeLikesNum(), debateRoom.getDisagreeLikesNum()),
                isIncrease
        );
    }

    private boolean updatePreference(Member member, Message message, PreferenceRequestDto dto){
        Optional<Preference> curPreference = preferenceRepository.findByMemberAndMessage(member, message);

        // 현재 preference 상태에 따라 적절히 처리
        if(curPreference.isEmpty()){ // null 일 경우 생성 후 저장
            Preference preference = dto.toPreference(member, message);
            preferenceRepository.save(preference);
            return true;
        } else { // Preference 가 이미 같은 상태로 있는 경우 삭제
            preferenceRepository.delete(curPreference.get());
            return false;
        }
    }

    private DebateRoom changePreferenceRatio(boolean isAgree,boolean isIncrease, DebateRoom debateRoom){
        long curPreference;
        if(isAgree){ // 찬성 입장 메세지인 경우
            curPreference = debateRoom.getAgreeLikesNum();
            if(isIncrease)
                debateRoom.setAgreeLikesNum(curPreference + 1);
            else
                debateRoom.setAgreeLikesNum(curPreference - 1);
        } else{ // 반대 입장 메세지인 경우
            curPreference = debateRoom.getDisagreeLikesNum();
            if(isIncrease)
                debateRoom.setDisagreeLikesNum(curPreference + 1);
            else
                debateRoom.setDisagreeLikesNum(curPreference - 1);
        }
        debateRoomRepository.save(debateRoom);
        return debateRoom;
    }

    private long getPreferenceNum(Message message){
        return preferenceRepository.findByMessage(message).size();
    }

    private long getMessageThreadNum(Message message){
        return messageThreadRepository.findByMessage(message).size();
    }

    private PreferenceResponseDto makeDto(double ratio, boolean isIncrease){
        return PreferenceResponseDto.builder()
                .ratio(ratio)
                .isIncrease(isIncrease)
                .build();
    }
}
