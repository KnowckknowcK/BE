package com.knu.KnowcKKnowcK.controller;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponse;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.dto.requestdto.PreferenceRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.MessageThreadResponseDto;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
@Tag(name="Message in Debate Room", description = "토론방 내 메세지와 관련된 기능을 제공하는 API Controller")
public class MessageController {

    private final MessageService messageService;
    private final MemberRepository memberRepository;
    private Member member;
    @GetMapping("/{debateRoomId}")
    @Operation(summary = "메세지 요청 API", description = "토론방에 올라온 메세지 리스트를 받을 때 요청하는 API")
    @Parameters({@Parameter(name = "debateRoomId", description = "메세지를 받고자 하는 토론방 ID", example = "2")})
    public ApiResponse<List<MessageResponseDto>> getMessages(@PathVariable Long debateRoomId){
        member = memberRepository.findById(1L).orElse(null);
        return ApiResponse.success(SuccessCode.OK, messageService.getMessages(member, debateRoomId));
    }

    @GetMapping("/thread/{messageId}")
    @Operation(summary = "스레드 메세지 요청 API", description = "특정 메세지에 대한 스레드 리스트를 받을 때 요청하는 API")
    @Parameters({@Parameter(name = "messageId", description = "스레드를 받길 바라는 특정 메세지 ID", example = "3")})
    public ApiResponse<List<MessageThreadResponseDto>> getMessageThread(@PathVariable Long messageId) {
        return ApiResponse.success(SuccessCode.OK, messageService.getMessageThreadDtoList(messageId));
    }

    @PutMapping("/preference/{messageId}")
    @Operation(summary = "좋아요/싫어요 추가 API", description = "특정 메세지에 대한 좋아요/싫어요를 표시할 때 요청하는 API")
    @Parameters({
            @Parameter(name = "messageId", description = "좋아/싫어요를 추가하길 바라는 특정 메세지 ID", example = "3"),
            @Parameter(name = "PreferenceRequestDto", description = "좋아요/싫어요 추가 요청 바디", example = "{'isLike': true}")
    })
    public ApiResponse<Double> putPreference(@PathVariable Long messageId, @RequestBody PreferenceRequestDto preferenceRequestDTO) {
        member = memberRepository.findById(1L).orElse(null);
        return ApiResponse.success(SuccessCode.OK, messageService.putPreference(member, messageId, preferenceRequestDTO));
    }
}
