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
            @Parameter(name = "id", description = "임시 테스트용 추후 수정 예정", example = "1")
    })
    @GetMapping("/info")
    public ApiResponseDto<ProfileResponseDto> getMyProfile(@RequestHeader("Authorization") Long id){
        ProfileResponseDto response = myPageService.getProfile(id);
        return ApiResponseDto.success(SuccessCode.OK,response);
    }

    @PatchMapping("/info")
    @Operation(summary="프로필 수정 API",description="프로필 정보 수정 요청을 처리한다.")
    @Parameters({
            @Parameter(name = "id", description = "임시 테스트용 추후 수정 예정", example = "1"),
            @Parameter(name = "request", description = "수정정보 요청 Dto", example = "name : Test, email: test@gmail.com, profileImage: test.jpg"),
    }
    )
    public ApiResponseDto<String> updateMyProfile(Long id, @RequestBody ProfileUpdateRequestDto request){
        myPageService.updateProfile(id,request);
        return ApiResponseDto.success(SuccessCode.OK,"변경이 성공적으로 됐습니다.");
    }

    @GetMapping("/dashboard")
    @Operation(summary = "대시보드 조회",description = "총 요약,견해 작성 횟수, 연속 참여 횟수, 오늘 도전 횟수 등을 조회할 수 있다.")
    @Parameters({
            @Parameter(name = "id", description = "임시 테스트용 유저 식별", example = "1")
    })
    public ApiResponseDto<DashboardResponseDto> getDashboardInfo(Long id){
        return ApiResponseDto.success(SuccessCode.OK,myPageService.getDashboardInfo(id));
    }
}
