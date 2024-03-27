package com.knu.KnowcKKnowcK.controller;

import com.knu.KnowcKKnowcK.dto.MessageDTO;
import com.knu.KnowcKKnowcK.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessagingTemplate template;
    private final MessageService messageService;
    @MessageMapping("/chat/message")
    public void sendMessage(MessageDTO messageDTO) {
        messageService.saveMessage(messageDTO);
        template.convertAndSend("/sub/chat/room/" + messageDTO.getRoomId(), messageDTO);
    }

    @RequestMapping("/message/{debateRoomId}")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable Long debateRoomId){
        return ResponseEntity.ok(messageService.getMessages(debateRoomId));
    }
}
