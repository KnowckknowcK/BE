package com.knu.KnowcKKnowcK.config.jwtConfig;

import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.service.account.TokenService;
import com.knu.KnowcKKnowcK.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static com.knu.KnowcKKnowcK.exception.ErrorCode.TOKEN_EXPIRED;
import static com.knu.KnowcKKnowcK.exception.ErrorCode.TOKEN_INVALID;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private static final String[] WHITE_LIST = {
            "/actuator/**",
            "/profile","/redis",
            "/api/account/**",
            "/api-docs/**", "/v3/api-docs/**", "/swagger-ui/**",
            "/api/ws/**",
            "/",
            "/login/oauth2/code/google", "/oauth2/authorization/google",
            "/api/article/recommended",
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);

        String path = request.getServletPath();
        PathMatcher pathMatcher = new AntPathMatcher();

        boolean isWhiteList = Arrays.stream(WHITE_LIST).anyMatch(whitePath -> pathMatcher.match(whitePath, path));
        if (isWhiteList) {
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (jwtUtil.validateToken(token)) {
                try {
                    Authentication auth = jwtUtil.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } catch (CustomException e) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("GetAuthentication failed error_");
                } catch (Exception e) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("Authentication failed error_");
                }
            }
        } catch (CustomException e) {
            if (e.getErrorCode() == TOKEN_EXPIRED) {
                //만료 표시 419
                response.setStatus(419);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("AccessToken Expired");
                return;

            } else if (e.getErrorCode() == TOKEN_INVALID) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("Invalid AccessToken error_");
            }
        }
        filterChain.doFilter(request, response);
    }
}