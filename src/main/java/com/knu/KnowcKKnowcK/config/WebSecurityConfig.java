package com.knu.KnowcKKnowcK.config;

import com.knu.KnowcKKnowcK.config.jwtConfig.JwtSecurityConfig;
import com.knu.KnowcKKnowcK.exception.jwtHandler.JwtAccessDeniedHandler;
import com.knu.KnowcKKnowcK.exception.jwtHandler.JwtAuthenticationEntryPoint;
import com.knu.KnowcKKnowcK.utils.JwtUtil;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private static final String[] WHITE_LIST = {
            "/api/account/**",
            "/api-docs/**", "v3/api-docs/**", "swagger-ui/**",
            "/api/ws/**",
            "/",
            "/login/oauth2/code/google", "/oauth2/authorization/google"
    };

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
//                        .anyRequest().permitAll()
                        .requestMatchers(WHITE_LIST).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .oauth2Login(Customizer.withDefaults());

        httpSecurity.apply(new JwtSecurityConfig(jwtUtil));

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
