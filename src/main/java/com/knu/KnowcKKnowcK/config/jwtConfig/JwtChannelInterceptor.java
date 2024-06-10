package com.knu.KnowcKKnowcK.config.jwtConfig;

import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static com.knu.KnowcKKnowcK.exception.ErrorCode.TOKEN_INVALID;
import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {
    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        // WebSocket 연결 시 인증 수행
        if (nonNull(accessor) && StompCommand.CONNECT.equals(accessor.getCommand())) {
            try {
                String token = null;
                String authHeader = accessor.getFirstNativeHeader("Authorization");
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);
                }
                // 토큰 검증 및 인증 처리
                if (nonNull(token) && jwtUtil.validateToken(token)) {
                    Authentication auth = jwtUtil.getAuthentication(token);
                    accessor.setUser(auth);
                    return message;
                } else {
                    // 유효하지 않은 토큰의 경우 연결 거부
                    throw new CustomException(TOKEN_INVALID);
                }
            } catch (Exception e) {
                log.warn("Authentication failed: {}", e.getMessage());
                throw new CustomException(TOKEN_INVALID);
            }
        }
        return message;
    }
}