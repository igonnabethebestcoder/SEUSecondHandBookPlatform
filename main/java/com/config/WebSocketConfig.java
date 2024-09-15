package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

import com.services.ChatHandler;
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
    @Bean
    public ChatHandler chatHandler() {
        return new ChatHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler(), "/chat")
                .addInterceptors(new HttpSessionHandshakeInterceptor(), new UserHandshakeInterceptor())
                .setAllowedOrigins("*");
    }
}

class UserHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(
        ServerHttpRequest request, ServerHttpResponse response, 
        WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        
        // 获取当前用户
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        attributes.put("user", userName);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // Do nothing
    }
}

