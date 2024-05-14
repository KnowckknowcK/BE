package com.knu.KnowcKKnowcK.authenticationHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knu.KnowcKKnowcK.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final Long expiredAt = 1000 * 60 * 60L; //1H

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");
        String profileImg = (String) attributes.get("picture");
        String accessToken = JwtUtil.creatJWT(email, expiredAt);

        Map<String, String> memberInfo = new HashMap<>();
        memberInfo.put("name", name);
        memberInfo.put("email", email);
        memberInfo.put("profileImg", profileImg);
        memberInfo.put("token", accessToken);

        // 사용자 정보를 JSON으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(memberInfo);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 사용자 정보를 response 바디에 추가
        response.getWriter().write(userJson);
        //상태코드 200
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
