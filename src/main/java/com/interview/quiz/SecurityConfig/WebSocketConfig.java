package com.interview.quiz.SecurityConfig;

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
        // clients will connect to /signal using SockJS
        registry.addEndpoint("/signal")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // messages sent from client to /app/** go to @MessageMapping
        config.setApplicationDestinationPrefixes("/app");
        // messages from server to /topic/** are broadcasted to subscribers
        config.enableSimpleBroker("/topic");
    }
}
