package com.dadam.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	// 클라이언트가 구독할 때 사용할 브로커 주소 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 클라이언트가 서버에 메시지를 보낼 때는 /app 으로 시작하는 주소를 사용
        config.setApplicationDestinationPrefixes("/app");
        // 서버가 클라이언트에게 메시지를 보낼 때는 /topic 으로 시작하는 주소로 브로드캐스트
        config.enableSimpleBroker("/topic");
    }

    // 클라이언트가 접속할 웹소켓 엔드포인트 설정 
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트는 /ws/chat 으로 접속할 것
        registry.addEndpoint("/ws/chat").setAllowedOriginPatterns("*").withSockJS();
    }
}
