package com.knu.KnowcKKnowcK.authenticationHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${client.local.url}")
    private String clientLocalUrl;

    @Value("${client.base.url}")
    private String clientBaseUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");

//        String redirectUrl =  "http://localhost:3000/" + "google-login?email=" + email;
        String redirectUrl =  "https://www.knowckknowck.com/" + "google-login?email=" + email;

        response.sendRedirect(redirectUrl);
    }
}
