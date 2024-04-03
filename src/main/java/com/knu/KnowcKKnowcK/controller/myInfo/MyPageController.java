package com.knu.KnowcKKnowcK.controller.myInfo;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponseDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.domain.DebateRoom;
import com.knu.KnowcKKnowcK.domain.Member;
import com.knu.KnowcKKnowcK.dto.requestdto.ProfileRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.ProfileResponseDto;
import com.knu.KnowcKKnowcK.service.myPage.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
@Tag(name = "MyPage",description = "마이페이지에 관한 요청을 처리한다.")
public class MyPageController {
    //TODO 참여중인 토론방 목록 확인
    //TODO 현재 작성 중인 혹은 작성한 요약문 확인
    //TODO 작성한 견해문 확인

    private final MyPageService myPageService;

    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    //헤더는 로그인 기능 이후 수정 예정

    @Operation(summary="프로필 정보 API",description="사용자가 프로필 정보를 요청했을 때 보여준다")
    @Parameters({@Parameter(name = "email", description = "임시 테스트용 추후 수정 예정", example = "example@gmail.com")})
    @GetMapping("/profile/info")
    public ApiResponseDto<ProfileResponseDto> getMyProfile(@RequestHeader("Authorization") String email){
        ProfileResponseDto response = myPageService.getProfile(email);
        return ApiResponseDto.success(SuccessCode.OK,response);
    }

    @PatchMapping("/profile/info")
    @Operation(summary="프로필 수정 API",description="프로필 정보 수정 요청을 처리한다.")
    @Parameters({
            @Parameter(name = "email", description = "임시 테스트용 추후 수정 예정", example = "example@gmail.com"),
            @Parameter(name = "request", description = "수정정보 요청 Dto", example = "name : Test, email: test@gmail.com, profileImage: test.jpg")}
    )
    public ApiResponseDto<String> updateMyProfile(@RequestHeader("Authorization") String email,
                                                              @RequestBody ProfileRequestDto request){
        myPageService.updateProfile(email,request);
        return ApiResponseDto.success(SuccessCode.OK,"변경이 성공적으로 됐습니다.");
    }

    //참여 중인 토론방 확인
//    @GetMapping("debate-room/ing")
//    public ApiResponseDto<ArrayList<DebateRoom>> getMyDebateRoom(@RequestHeader("Authorization") String email){
//        List<DebateRoom> debateRooms = myPageService.getDebateRoom(email);
//    }
}