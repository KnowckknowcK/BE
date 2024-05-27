package com.knu.KnowcKKnowcK.authenticationHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.knu.KnowcKKnowcK.exception.ErrorCode.UNAUTHORIZED;

/**
 * 유저 정보는 있지만 접근할 수 있는 권한이 없는 경우 : UNAUTHORIZED(접근 권한이 없습니다) (403) 응답
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.setStatus(UNAUTHORIZED.getStatus());
        response.getWriter().write(UNAUTHORIZED.getMessage());
    }
}