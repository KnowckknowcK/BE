package com.knu.KnowcKKnowcK.config;

import com.knu.KnowcKKnowcK.config.jwtConfig.JwtChannelInterceptor;
import com.knu.KnowcKKnowcK.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${client.base.url}")
    private String clientUrl;
    @Value("${client.local.url}")
    private String clientLocalUrl;
    private final JwtUtil jwtUtil;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/ws")
                .setAllowedOrigins(clientLocalUrl, clientUrl).withSockJS();

//                .setInterceptors(new JwtChannelInterceptor(jwtUtil));
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지를 구독하는 요청의 prefix를 설정.
        registry.enableSimpleBroker("/sub");
        // 메시지를 보내는 요청의 prefix를 설정.
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new JwtChannelInterceptor(jwtUtil));
    }

}
