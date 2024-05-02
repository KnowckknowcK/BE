package com.knu.KnowcKKnowcK.config;

import com.knu.KnowcKKnowcK.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AccountService accountService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        // 개발 단계에서의 임시 설정
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                                .anyRequest().permitAll()
//                        .requestMatchers("/api/account/**").permitAll()
//                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtFilter(accountService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
