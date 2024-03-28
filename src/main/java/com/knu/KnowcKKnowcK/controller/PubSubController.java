package com.knu.KnowcKKnowcK.controller;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.dto.requestdto.MessageRequestDTO;
import com.knu.KnowcKKnowcK.dto.requestdto.MessageThreadRequestDTO;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PubSubController {
    private final SimpMessagingTemplate template;
    private final MessageService messageService;
    private final MemberRepository memberRepository;
    private Member member;
    @MessageMapping(value = "/message")
    @Operation(summary = "메세지 보내기", description = "클라이언트가 토론방에 메세지를 보낼 때 요청하는 API")
    @Parameters({
            @Parameter(name = "MessageRequestDTO", description = "보낼 메세지 요청 바디",
                    example = "['roomId': 3, 'content': '보내길 원하는 메세지 내용']")
    })
    public void sendMessage(MessageRequestDTO messageRequestDTO) {
        member = memberRepository.findById(1L).orElse(null);
        messageService.saveMessage(member, messageRequestDTO);
        template.convertAndSend("/sub/room/" + messageRequestDTO.getRoomId(), messageRequestDTO);
    }

    @MessageMapping(value = "/message/{messageId}")
    @Operation(summary = "스레드에 메세지 보내기", description = "클라이언트가 메세지 스레드에 댓글을 달 때 요청하는 API")
    @Parameters({
            @Parameter(name = "messageId", description = "메세지 스레드를 추가하길 바라는 메세지 ID", example = "3"),
            @Parameter(name = "MessageThreadRequestDTO", description = "보낼 메세지 스레드 요청 바디",
                    example = "['roomId': 3, 'content': '보내길 원하는 메세지 스레드 내용']")
    })
    public void sendMessageThread(@DestinationVariable Long messageId, MessageThreadRequestDTO messageThreadRequestDTO) {
        member = memberRepository.findById(1L).orElse(null);
        messageService.saveMessageThread(member, messageId,messageThreadRequestDTO);
        template.convertAndSend("/sub/room/" +
                        messageThreadRequestDTO.getRoomId() +
                        messageId,
                messageThreadRequestDTO);
    }

}
