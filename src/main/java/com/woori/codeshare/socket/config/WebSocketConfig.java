package com.woori.codeshare.socket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocket                // WebSocket 활성화
@EnableWebSocketMessageBroker   // STOMP 활성화
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트가 구독할 경로 설정 (브로드캐스트 전송 경로)
        registry.enableSimpleBroker("/topic");
        // 클라이언트가 서버로 메시지를 보낼 경로 설정
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 WebSocket 서버에 연결할 엔드포인트 설정
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("http://localhost:3000", "https://www.wooricodeshare.com")
                .withSockJS();  // SockJS 사용 설정 (WebSocket 미지원 브라우저에서도 폴백 가능하도록)
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> converters) {
        converters.add(new MappingJackson2MessageConverter());
        return false;  // 기본 메시지 컨버터 유지
    }

}
