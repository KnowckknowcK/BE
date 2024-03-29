package com.knu.KnowcKKnowcK.service;


import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.dto.requestdto.MessageRequestDTO;
import com.knu.KnowcKKnowcK.dto.requestdto.MessageThreadRequestDTO;
import com.knu.KnowcKKnowcK.dto.requestdto.PreferenceDTO;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageResponseDTO;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageThreadResponseDTO;
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

    public void saveMessage(Member member, MessageRequestDTO messageRequestDTO){
        DebateRoom debateRoom = debateRoomRepository.findById(messageRequestDTO.getRoomId()).orElseThrow();
        messageRepository.save(messageRequestDTO.toMessage(member, debateRoom));
    }
    public void saveMessageThread(Member member, Long messageId, MessageThreadRequestDTO messageThreadRequestDTO) {
        Message message = messageRepository.findById(messageId).orElseThrow();
        messageThreadRepository.save(messageThreadRequestDTO.toMessageThread(member, message));
    }

    public List<MessageResponseDTO> getMessages(Long roomId){
        DebateRoom debateRoom = debateRoomRepository.findById(roomId).orElseThrow();
        List<Message> messageList = messageRepository.findByDebateRoom(debateRoom);

        List<MessageResponseDTO> messageResponseDTOList = new ArrayList<>();
        for (Message message: messageList) {
            messageResponseDTOList.add(message.toMessageDTO());
        }
        return messageResponseDTOList;
    }

    public List<MessageThreadResponseDTO> getMessageThreadDTOList(Long messageId){
        Message message = messageRepository.findById(messageId).orElseThrow();
        List<MessageThread> messageThraedList = messageThreadRepository.findByMessage(message);

        List<MessageThreadResponseDTO> messageThreadResponseDTOList = new ArrayList<>();
        for (MessageThread messageThread: messageThraedList) {
            messageThreadResponseDTOList.add(messageThread.toMessageThreadDTO(messageId));
        }
        return messageThreadResponseDTOList;
    }

    public String putPreference(Member member, Long messageId, PreferenceDTO preferenceDTO){
        updatePreference(member, messageId, preferenceDTO);
        // 토론방 내부 ratio 변경 필요
        return "success!!";
    }

    private void updatePreference(Member member, Long messageId, PreferenceDTO preferenceDTO){
        // 로직 개선 가능성 있음, 아이디어 생기면 수정할 예정
        Message message = messageRepository.findById(messageId).orElseThrow();
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
