package com.knu.KnowcKKnowcK.controller;

import com.knu.KnowcKKnowcK.dto.MessageDTO;
import com.knu.KnowcKKnowcK.dto.PreferenceDTO;
import com.knu.KnowcKKnowcK.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("message")
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessagingTemplate template;
    private final MessageService messageService;
    @MessageMapping("")
    public void sendMessage(MessageDTO messageDTO) {
        messageService.saveMessage(messageDTO);
        template.convertAndSend("/sub/room/" + messageDTO.getRoomId(), messageDTO);
    }

    @GetMapping("/{debateRoomId}")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable Long debateRoomId){
        return ResponseEntity.ok(messageService.getMessages(debateRoomId));
    }

    @GetMapping("/{messageId}")
    public void getMessageThread(@PathVariable Long messageId) {
        // TODO: 특정 메세지에 대한 메세지 스레드 반환해주기

    }

    @PutMapping("/preference/{messageId}")
    public void putPreference(@PathVariable Long messageId, PreferenceDTO preferenceDTO) {
        // TODO: 특정 메세지에 대한 좋아요/싫어요 표시해주기

    }
}
