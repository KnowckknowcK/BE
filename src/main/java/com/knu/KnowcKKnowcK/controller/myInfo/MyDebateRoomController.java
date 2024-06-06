package com.knu.KnowcKKnowcK.controller.myInfo;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponseDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.dto.responsedto.MyDebateRoomResponseDto;
import com.knu.KnowcKKnowcK.service.myPage.MyDebateroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/debate-room")
@RequiredArgsConstructor
@Tag(name = "MyDebateroom ",description = "사용자가 참여 중인 토론방에 관한 처리")
public class MyDebateRoomController {
    private final MyDebateroomService myDebateroomService;

    @GetMapping("/ing")
    @Operation(summary = "참여 중인 토론방 확인 API", description = "현재 참여 중인 토론방 확인 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토론방 목록 조회 성공"),
    })
    public ApiResponseDto<List<MyDebateRoomResponseDto>> getDebateRoom(Authentication authentication){
        List<MyDebateRoomResponseDto> debateRooms =  myDebateroomService.getDebateRoom(authentication.getName());
        return ApiResponseDto.success(SuccessCode.OK,debateRooms);
    }
}
