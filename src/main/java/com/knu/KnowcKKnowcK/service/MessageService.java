package com.knu.KnowcKKnowcK.service;


import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Message;
import com.knu.KnowcKKnowcK.dto.MessageDTO;
import com.knu.KnowcKKnowcK.repository.DebateRoomRepository;
import com.knu.KnowcKKnowcK.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final DebateRoomRepository debateRoomRepository;
    public void saveMessage(MessageDTO messageDTO){
        DebateRoom debateRoom = debateRoomRepository.findById(messageDTO.getRoomId()).orElse(null);
        messageRepository.save(messageDTO.toMessage(debateRoom));
    }

    public List<MessageDTO> getMessages(Long roomId){
        DebateRoom debateRoom = debateRoomRepository.findById(roomId).orElse(null);
        List<Message> messageList = messageRepository.findByDebateRoomId(debateRoom);
        List<MessageDTO> messageDTOList = new ArrayList<>();
        for (Message message: messageList) {
            messageDTOList.add(message.toMessageDTO());
        }

        return messageDTOList;
    }

    // TODO: 특정 메세지에 대한 메세지 스레드 반환

    // TODO: 특정 메세지에 대한 메세지 좋아요/싫어요 변경 -> 토론방 내부 ratio 변경 필요


}
