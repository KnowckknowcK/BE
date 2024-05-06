package com.knu.KnowcKKnowcK.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${client.base.url}")
    private String clientUrl;
    @Value("${client.local.url}")
    private String clientLocalUrl;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 WebSocket 연결을 맺을 수 있는 엔드포인트를 설정/ 현재는 로컬만, 배포할 경우 테스팅 필요
        registry.addEndpoint("/api/ws")
                .setAllowedOrigins(clientLocalUrl, clientUrl).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지를 구독하는 요청의 prefix를 설정.
        registry.enableSimpleBroker("/sub");
        // 메시지를 보내는 요청의 prefix를 설정.
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
