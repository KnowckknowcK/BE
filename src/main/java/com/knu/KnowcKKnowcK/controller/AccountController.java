package com.knu.KnowcKKnowcK.controller;

import com.knu.KnowcKKnowcK.apiResponse.ApiResponseDto;
import com.knu.KnowcKKnowcK.apiResponse.SuccessCode;
import com.knu.KnowcKKnowcK.dto.requestdto.MailCheckRequestDto;
import com.knu.KnowcKKnowcK.dto.requestdto.SigninRequestDto;
import com.knu.KnowcKKnowcK.dto.requestdto.SignupRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SigninResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.SignupResponseDto;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.service.account.AccountService;
import com.knu.KnowcKKnowcK.service.account.MailService;
import com.knu.KnowcKKnowcK.utils.MailUtil;
import com.knu.KnowcKKnowcK.utils.RedisUtil;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.TemplateEngine;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
@Tag(name="Account",description="소셜, 자체 회원가입 및 로그인과 관련된 API Controller")
public class AccountController {

    private final AccountService accountService;
    private final MailService mailService;



    @GetMapping("/google")
    @Operation(summary = "구글 로그인 및 회원가입 API", description = "구글 로그인 및 회원가입에 대한 API")
    public RedirectView redirectToGoogle() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/oauth2/authorization/google");
        return redirectView;
    }

    @PostMapping("/sign-in")
    @Operation(summary = "자체 로그인 API", description = "이메일 회원가입한 사용자의 로그인 요청 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "로그인 실패: id/pw 불일치"),
            @ApiResponse(responseCode = "404", description = "로그인 실패: 사용자를 찾을 수 없음"),
            @ApiResponse(responseCode = "409", description = "로그인 실패: OAuth 사용 회원")
    })
    public ApiResponseDto<SigninResponseDto> signinWithEmail(@RequestBody SigninRequestDto requestDto) {
        SigninResponseDto responseDto = accountService.signinWithEmail(requestDto.getEmail(), requestDto.getPassword());
        return ApiResponseDto.success(SuccessCode.OK, responseDto);
    }

    @PostMapping("/sign-up")
    @Operation(summary = "자체 회원가입 API", description = "이메일 회원가입 요청 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "회원가입 실패"),
            @ApiResponse(responseCode = "409", description = "회원가입 실패: 이미 가입 된 email")
    })
    public ApiResponseDto<SignupResponseDto> signupWithEmail(@RequestBody SignupRequestDto requestDto) {
        SignupResponseDto responseDto = accountService.signupWithEmail(
                requestDto.getEmail(), requestDto.getName(), requestDto.getPassword(), requestDto.getProfileImg());
        return ApiResponseDto.success(SuccessCode.OK, responseDto);
    }

    @PostMapping("/email-check")
    @Operation(summary = "이메일 인증 API", description = "이메일 인증 코드 발송 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증코드 발송 성공"),
            @ApiResponse(responseCode = "400", description = "인증코드 발송 실패")
    })
    public  ApiResponseDto<String> mailCheck(@RequestBody MailCheckRequestDto requestDto){
        try{
            String response = mailService.sendMail(requestDto.getEmail());
            return ApiResponseDto.success(SuccessCode.OK, response);
        } catch (MessagingException e) {
            throw new CustomException(ErrorCode.FAILED);
        }
    }
}
