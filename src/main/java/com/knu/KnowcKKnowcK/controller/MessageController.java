package com.knu.KnowcKKnowcK.controller;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.dto.requestdto.MessageRequestDTO;
import com.knu.KnowcKKnowcK.dto.requestdto.MessageThreadRequestDTO;
import com.knu.KnowcKKnowcK.dto.requestdto.PreferenceDTO;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageResponseDTO;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageThreadResponseDTO;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/message")
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessagingTemplate template;
    private final MessageService messageService;
    private final MemberRepository memberRepository;
    private Member member;

    @MessageMapping("")
    public void sendMessage(MessageRequestDTO messageRequestDTO) {
        messageService.saveMessage(messageRequestDTO);
        template.convertAndSend("/sub/room/" + messageRequestDTO.getRoomId(), messageRequestDTO);
    }

    @MessageMapping("/{messageId}")
    public void sendMessageThread(@DestinationVariable Long messageId, MessageThreadRequestDTO messageThreadRequestDTO) {
        member = memberRepository.findById(1L).orElse(null);
        messageService.saveMessageThread(member, messageId,messageThreadRequestDTO);
        template.convertAndSend("/sub/room/" +
                messageThreadRequestDTO.getRoomId() +
                messageId,
                messageThreadRequestDTO);
    }

    @GetMapping("/{debateRoomId}")
    public ResponseEntity<List<MessageResponseDTO>> getMessages(@PathVariable Long debateRoomId){
        return ResponseEntity.ok(messageService.getMessages(debateRoomId));
    }

    @GetMapping("/thread/{messageId}")
    public ResponseEntity<List<MessageThreadResponseDTO>> getMessageThread(@PathVariable Long messageId) {
        return ResponseEntity.ok(messageService.getMessageThreadDTOList(messageId));
    }

    @PutMapping("/preference/{messageId}")
    public ResponseEntity<String> putPreference(@PathVariable Long messageId, PreferenceDTO preferenceDTO) {
        // DTO 빈 경우 예외처리 필요
        member = memberRepository.findById(1L).orElse(null);
        return ResponseEntity.ok(messageService.putPreference(member, messageId, preferenceDTO));
    }
}
