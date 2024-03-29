package com.knu.KnowcKKnowcK.service;


import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.dto.requestdto.MessageRequestDto;
import com.knu.KnowcKKnowcK.dto.requestdto.MessageThreadRequestDto;
import com.knu.KnowcKKnowcK.dto.requestdto.PreferenceDto;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageThreadResponseDto;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final DebateRoomRepository debateRoomRepository;
    private final MessageRepository messageRepository;
    private final MessageThreadRepository messageThreadRepository;
    private final PreferenceRepository preferenceRepository;

    public void saveMessage(Member member, MessageRequestDto messageRequestDTO){
        DebateRoom debateRoom = debateRoomRepository.findById(messageRequestDTO.getRoomId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        messageRepository.save(messageRequestDTO.toMessage(member, debateRoom));
    }
    public void saveMessageThread(Member member, Long messageId, MessageThreadRequestDto messageThreadRequestDTO) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        messageThreadRepository.save(messageThreadRequestDTO.toMessageThread(member, message));
    }

    public List<MessageResponseDto> getMessages(Long roomId){
        DebateRoom debateRoom = debateRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        List<Message> messageList = messageRepository.findByDebateRoom(debateRoom);

        List<MessageResponseDto> messageResponseDtoList = new ArrayList<>();
        for (Message message: messageList) {
            messageResponseDtoList.add(message.toMessageDto());
        }
        return messageResponseDtoList;
    }

    public List<MessageThreadResponseDto> getMessageThreadDTOList(Long messageId){
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        List<MessageThread> messageThraedList = messageThreadRepository.findByMessage(message);

        List<MessageThreadResponseDto> messageThreadResponseDtoList = new ArrayList<>();
        for (MessageThread messageThread: messageThraedList) {
            messageThreadResponseDtoList.add(messageThread.toMessageThreadDto(messageId));
        }
        return messageThreadResponseDtoList;
    }

    public String putPreference(Member member, Long messageId, PreferenceDto preferenceDTO){
        updatePreference(member, messageId, preferenceDTO);
        // 토론방 내부 ratio 변경 필요
        return "success!!";
    }

    private void updatePreference(Member member, Long messageId, PreferenceDto preferenceDTO){
        // 로직 개선 가능성 있음, 아이디어 생기면 수정할 예정
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        Optional<Preference> curPreference = preferenceRepository.findByMemberAndMessage(member, message);
        // 현재 preference 상태에 따라 적절히 처리
        if(curPreference.isEmpty()){ // null 일 경우 생성 후 저장
            Preference preference = preferenceDTO.toPreference(member, message);
            preferenceRepository.save(preference);
        } // Preference 가 이미 같은 상태로 있는 경우 삭제
        else if(curPreference.get().isLike() == preferenceDTO.isLike()){
            preferenceRepository.delete(curPreference.get());
        } else { // preference 가 반대의 상태라면 바꾸고 저장
            curPreference.get().setLike(preferenceDTO.isLike());
            preferenceRepository.save(curPreference.get());
        }
    }
}
