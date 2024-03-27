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
}
