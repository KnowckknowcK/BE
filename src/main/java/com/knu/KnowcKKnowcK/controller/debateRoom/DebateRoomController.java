package com.knu.KnowcKKnowcK.controller.debateRoom;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponseDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.dto.responsedto.DebateRoomMemberDto;
import com.knu.KnowcKKnowcK.dto.responsedto.DebateRoomResponseDto;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.MemberRepository;
import com.knu.KnowcKKnowcK.service.debateRoom.DebateRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/debate-room")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name="Debate Room", description = "토론방 참여/나가기와 관련된 API Controller")
public class DebateRoomController {
    final private DebateRoomService debateRoomService;
    private final MemberRepository memberRepository;
    private Member member;

    @PutMapping("/{debateRoomId}")
    @Operation(summary = "토론방 참여 API", description = "클라이언트가 토론방에 참여하길 바랄 때 요청하는 API")
    @Parameters({@Parameter(name = "debateRoomId", description = "참여하길 바라는 토론방 ID", example = "3")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토론방 참여 성공"),
            @ApiResponse(responseCode = "400", description = "토론방 참여 실패")
    })
    public ApiResponseDto<DebateRoomResponseDto> participateInDebateRoom(
            @PathVariable("debateRoomId") Long debateRoomId,
            Authentication authentication
    ){
        member = memberRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        return ApiResponseDto.success(SuccessCode.OK, debateRoomService.participateInDebateRoom(member, debateRoomId));
    }

    @DeleteMapping("/{debateRoomId}")
    @Operation(summary = "토론방 나가기 API", description = "클라이언트가 토론방에서 나가길 바랄 때 요청하는 API")
    @Parameters({@Parameter(name = "debateRoomId", description = "나가길 원하는 토론방 ID", example = "3")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토론방 나가기 성공"),
            @ApiResponse(responseCode = "400", description = "토론방 나가기 실패")
    })
    public ApiResponseDto<String> leaveDebateRoom(
            @PathVariable("debateRoomId") Long debateRoomId,
            Authentication authentication
    ){
        member = memberRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        return ApiResponseDto.success(SuccessCode.OK, debateRoomService.leaveDebateRoom(member, debateRoomId));
    }

    @GetMapping("/{debateRoomId}")
    @Operation(summary = "토론 참여자 리스트 API", description = "해당 토론방 참여자 리스트를 받아야할 때 요청하는 API")
    @Parameters({@Parameter(name = "debateRoomId", description = "참여자 리스트가 필요한 토론방 ID", example = "1")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토론방 참여자 리스트 반환"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 토론방")
    })
    public ApiResponseDto<ArrayList<DebateRoomMemberDto>> getDebateRoomMember(
            @PathVariable("debateRoomId") Long debateRoomId){
        return ApiResponseDto.success(SuccessCode.OK, debateRoomService.getDebateRoomMember(debateRoomId));
    }
}
