package com.knu.KnowcKKnowcK.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
@Tag(name="Account",description="소셜, 자체 회원가입 및 로그인과 관련된 API Controller")
public class AccountController {

    @GetMapping( "/google")
    @Operation(summary = "구글 로그인 및 회원가입 API", description = "구글 로그인 및 회원가입에 대한 API")
    public RedirectView redirectToGoogle() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/oauth2/authorization/google");
        return redirectView;
    }

}
