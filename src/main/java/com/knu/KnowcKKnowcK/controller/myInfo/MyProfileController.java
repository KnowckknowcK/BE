package com.knu.KnowcKKnowcK.controller.myInfo;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponseDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.dto.requestdto.ProfileUpdateRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.DashboardResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.ProfileResponseDto;
import com.knu.KnowcKKnowcK.service.myPage.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Tag(name = "MyPage",description = "마이페이지에 관한 요청을 처리한다.")
public class MyProfileController {

    private final MyPageService myPageService;
    //헤더는 로그인 기능 이후 수정 예정

    @Operation(summary="프로필 정보 API",description="사용자가 프로필 정보를 요청했을 때 보여준다")
    @Parameters({
    })
    @GetMapping("/info")
    public ApiResponseDto<ProfileResponseDto> getMyProfile(Authentication authentication){
        ProfileResponseDto response = myPageService.getProfile(authentication.getName());
        return ApiResponseDto.success(SuccessCode.OK,response);
    }

    @PatchMapping("/info")
    @Operation(summary="프로필 수정 API",description="프로필 정보 수정 요청을 처리한다.")
    @Parameters({
            @Parameter(name = "request", description = "수정정보 요청 Dto", example = "name : Test, email: test@gmail.com, profileImage: test.jpg"),
    }
    )
    public ApiResponseDto<String> updateMyProfile(Authentication authentication, @RequestBody ProfileUpdateRequestDto request){
        myPageService.updateProfile(authentication.getName(),request);
        return ApiResponseDto.success(SuccessCode.OK,"변경이 성공적으로 됐습니다.");
    }

    @GetMapping("/dashboard")
    @Operation(summary = "대시보드 조회",description = "총 요약,견해 작성 횟수, 연속 참여 횟수, 오늘 도전 횟수 등을 조회할 수 있다.")
    @Parameters({
    })
    public ApiResponseDto<DashboardResponseDto> getDashboardInfo(Authentication authentication){
        String email = authentication.getName();
        return ApiResponseDto.success(SuccessCode.OK,myPageService.getDashboardInfo(email));
    }
}
