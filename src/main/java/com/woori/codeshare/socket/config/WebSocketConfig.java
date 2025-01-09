package com.woori.codeshare.socket.config;

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
        // 클라이언트가 WebSocket 서버에 연결할 엔드포인트 설정
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:63342", "http://localhost:8000")
                .withSockJS();  // SockJS 사용 설정 (WebSocket 미지원 브라우저에서도 폴백)
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트가 구독할 경로 설정 (브로드캐스트 전송 경로)
        registry.enableSimpleBroker("/topic");
        // 클라이언트가 서버로 메시지를 보낼 경로 설정
        registry.setApplicationDestinationPrefixes("/app");
    }
}
