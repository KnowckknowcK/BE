package com.knu.KnowcKKnowcK.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    public RedirectView redirectToGoogle() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/oauth2/authorization/google");
        return redirectView;
    }

}
