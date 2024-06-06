package com.knu.KnowcKKnowcK.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.knu.KnowcKKnowcK.apiResponse.ApiResponseDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Tag(name = "Profile",description = "사용자 프로필에 관한 요청을 처리.")
public class MyProfileController {

    private final MyPageService myPageService;

    @Operation(summary="프로필 정보 조회 API",description="사용자가 프로필 정보를 요청했을 때 보여준다.")
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
            @Parameter(name = "requestDto", description = "수정정보 요청 Dto", example = "name : Test, password : Password"),
            @Parameter(name = "profileImg", description = "프로필 사진", example = "test.jpg")
    }
    )
    public ApiResponseDto<String> updateMyProfile (
            Authentication authentication,
            @RequestPart(value = "requestDto") String requestDto,
            @RequestPart(value = "profileImg", required = false) MultipartFile profileImg
            ) throws JsonProcessingException
    {
        myPageService.updateProfile(authentication.getName(),requestDto,profileImg);
        return ApiResponseDto.success(SuccessCode.OK,"변경이 성공적으로 됐습니다.");
    }

    @GetMapping("/dashboard")
    @Operation(summary = "대시보드 조회 API",description = "총 요약,견해 작성 횟수, 연속 참여 횟수, 오늘 도전 횟수 등을 조회할 수 있다.")
    @Parameters({
    })
    public ApiResponseDto<DashboardResponseDto> getDashboardInfo(Authentication authentication){
        String email = authentication.getName();
        return ApiResponseDto.success(SuccessCode.OK,myPageService.getDashboardInfo(email));
    }
}
