package com.knu.KnowcKKnowcK.authenticationHandler;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.knu.KnowcKKnowcK.exception.ErrorCode.TOKEN_INVALID;
/**
 * 유저 정보 없이 접근한 경우 : TOKEN_INVALID(유효하지 않은 토큰: 로그인이 필요합니다) (401) 응답
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.setStatus(TOKEN_INVALID.getStatus());
        response.getWriter().write(TOKEN_INVALID.getMessage());
    }
}
