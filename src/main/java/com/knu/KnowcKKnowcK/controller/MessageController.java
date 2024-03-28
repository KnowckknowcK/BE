package com.knu.KnowcKKnowcK.controller;

import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.dto.requestdto.MessageRequestDTO;
import com.knu.KnowcKKnowcK.dto.requestdto.MessageThreadRequestDTO;
import com.knu.KnowcKKnowcK.dto.requestdto.PreferenceDTO;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageResponseDTO;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageThreadResponseDTO;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Message in Debate Room", description = "토론방 내 메세지와 관련된 기능을 제공하는 API Controller")
public class MessageController {
    private final SimpMessagingTemplate template;
    private final MessageService messageService;
    private final MemberRepository memberRepository;
    private Member member;

    @MessageMapping("")
    @Operation(summary = "메세지 보내기", description = "클라이언트가 토론방에 메세지를 보낼 때 요청하는 API")
    @Parameters({
            @Parameter(name = "MessageRequestDTO", description = "보낼 메세지 요청 바디",
                    example = "['roomId': 3, 'content': '보내길 원하는 메세지 내용']")
    })
    public void sendMessage(MessageRequestDTO messageRequestDTO) {
        messageService.saveMessage(messageRequestDTO);
        template.convertAndSend("/sub/room/" + messageRequestDTO.getRoomId(), messageRequestDTO);
    }

    @MessageMapping("/{messageId}")
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

    @GetMapping("/{debateRoomId}")
    @Operation(summary = "메세지 요청 API", description = "토론방에 올라온 메세지 리스트를 받을 때 요청하는 API")
    @Parameters({@Parameter(name = "debateRoomId", description = "메세지를 받고자 하는 토론방 ID", example = "2")})
    public ResponseEntity<List<MessageResponseDTO>> getMessages(@PathVariable Long debateRoomId){
        return ResponseEntity.ok(messageService.getMessages(debateRoomId));
    }

    @GetMapping("/thread/{messageId}")
    @Operation(summary = "스레드 메세지 요청 API", description = "특정 메세지에 대한 스레드 리스트를 받을 때 요청하는 API")
    @Parameters({@Parameter(name = "messageId", description = "스레드를 받길 바라는 특정 메세지 ID", example = "3")})
    public ResponseEntity<List<MessageThreadResponseDTO>> getMessageThread(@PathVariable Long messageId) {
        return ResponseEntity.ok(messageService.getMessageThreadDTOList(messageId));
    }

    @PutMapping("/preference/{messageId}")
    @Operation(summary = "좋아요/싫어요 추가 API", description = "특정 메세지에 대한 좋아요/싫어요를 표시할 때 요청하는 API")
    @Parameters({
            @Parameter(name = "messageId", description = "좋아효/싫어요를 추가하길 바라는 특정 메세지 ID", example = "3"),
            @Parameter(name = "PreferenceDTO", description = "좋아요/싫어요 추가 요청 바디", example = "['isLike': True]")
    })
    public ResponseEntity<String> putPreference(@PathVariable Long messageId, PreferenceDTO preferenceDTO) {
        // DTO 빈 경우 예외처리 필요
        member = memberRepository.findById(1L).orElse(null);
        return ResponseEntity.ok(messageService.putPreference(member, messageId, preferenceDTO));
    }
}
