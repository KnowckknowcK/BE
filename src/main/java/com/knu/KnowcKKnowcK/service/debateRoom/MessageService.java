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

import java.util.List;
import java.util.Optional;

import static com.knu.KnowcKKnowcK.service.debateRoom.DebateRoomUtil.getDebateRoomKey;
import static com.knu.KnowcKKnowcK.service.debateRoom.DebateRoomUtil.getMessageThreadKey;


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
        MemberDebate memberDebate = memberDebateRepository.findByMemberIdAndDebateRoomId(member.getId(), dto.getRoomId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));

        Message message = dto.toMessage(member, memberDebate.getDebateRoom(), memberDebate.getPosition());
        messageRepository.save(message);

        MessageResponseDto responseDto =
                message.toMessageResponseDto(
                        0, 0,
                        member.getProfileImage());

        // redis 캐시에 messageResponseDto를 리스트로 저장 -> 조회 속도 증가
        redisUtil.addDataToList(getDebateRoomKey(dto.getRoomId()), responseDto);
        return responseDto;
    }

    public MessageThreadResponseDto saveAndReturnMessageThread(Member member, Long messageId, MessageThreadRequestDto dto) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        MemberDebate memberDebate = memberDebateRepository.findByMemberIdAndDebateRoomId(member.getId(), dto.getRoomId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));

        MessageThread messageThread = dto.toMessageThread(member, message, memberDebate.getPosition());
        messageThreadRepository.save(messageThread);

        MessageThreadResponseDto responseDto = messageThread.toMessageThreadResponseDto(messageId,
                member.getProfileImage());
        // redis 캐시에 messageThreadResponseDto를 리스트로 저장 -> 조회 속도 증가
        redisUtil.addDataToList(getMessageThreadKey(messageId), responseDto);
        redisUtil.deleteDataList(getDebateRoomKey(dto.getRoomId()));
        return responseDto;
    }

    public List<MessageResponseDto> getMessages(Long roomId){
        DebateRoom debateRoom = debateRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));

        String key = getDebateRoomKey(debateRoom.getId());
        // redis에서 관련 채팅방 정보가 있는 지 먼저 탐색
        List<MessageResponseDto> messageResponseDtoList =
                redisUtil.getDataList(key, MessageResponseDto.class);

        // 없으면 값을 가져와서 캐시에 저장한 후 반환
        if (messageResponseDtoList.isEmpty()){
            List<Object[]> results = messageRepository.findMessagesWithCounts(debateRoom);

            for (Object[] result : results) {
                Message message = (Message) result[0];
                long messageThreadCount = (long) result[1];
                long preferenceCount = (long) result[2];
                String profileImage = (String) result[3];

                MessageResponseDto dto = message.toMessageResponseDto(
                        preferenceCount,
                        messageThreadCount,
                        profileImage
                );
                messageResponseDtoList.add(dto);
            }
            redisUtil.setDataList(key, messageResponseDtoList);
        }
        // 있으면 그 값을 반환
        return messageResponseDtoList;
    }

    public List<MessageThreadResponseDto> getMessageThreadDtoList(Long messageId){
        // redis에서 관련 채팅방 정보가 있는 지 먼저 탐색
        String key = getMessageThreadKey(messageId);
        List<MessageThreadResponseDto> messageThreadResponseDtoList =
                redisUtil.getDataList(key, MessageThreadResponseDto.class);

        // 없으면 값을 가져와서 캐시에 저장한 후 반환
        if(messageThreadResponseDtoList.isEmpty()){
            Message message = messageRepository.findByIdWithMessageThreadsAndMember(messageId)
                    .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));

            for (MessageThread messageThread: message.getMessageThreads()) {
                messageThreadResponseDtoList
                        .add(messageThread.toMessageThreadResponseDto(messageId,
                                message.getMember().getProfileImage()));
            }
            redisUtil.setDataList(key, messageThreadResponseDtoList);
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
        // 토론방 내부 ratio 변경
        DebateRoom debateRoom = changePreferenceRatio(
                dto.getIsAgree(),
                isIncrease,
                message.getDebateRoom());
        redisUtil.deleteDataList(getDebateRoomKey(debateRoom.getId()));
        return makeDto(
                debateRoom.getAgreeLikesNum(),
                debateRoom.getDisagreeLikesNum(),
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

    private PreferenceResponseDto makeDto(long agreeLikesNum, long disagreeLikesNum, boolean isIncrease){
        return PreferenceResponseDto.builder()
                .agreeLikesNum(agreeLikesNum)
                .disagreeLikesNum(disagreeLikesNum)
                .isIncrease(isIncrease)
                .build();
    }
}
