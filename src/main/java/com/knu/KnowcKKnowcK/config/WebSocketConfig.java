package com.knu.KnowcKKnowcK.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 WebSocket 연결을 맺을 수 있는 엔드포인트를 설정합니다.
        registry.addEndpoint("/api/ws").setAllowedOrigins("http://localhost:3000/").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지를 구독하는 요청의 prefix를 설정합니다.
        registry.enableSimpleBroker("/sub");
        // 메시지를 보내는 요청의 prefix를 설정합니다.
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
