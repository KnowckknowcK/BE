package com.knu.KnowcKKnowcK.authenticationHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

import static com.knu.KnowcKKnowcK.exception.ErrorCode.OAUTH_LOGIN_FAIL;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.setStatus(OAUTH_LOGIN_FAIL.getStatus());
        response.getWriter().write("구글 로그인/회원가입 실패. 관리자에게 문의하세요.");
    }
}
