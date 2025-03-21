package com.boltie.chatservice.config;

import com.boltie.chatservice.interceptors.AuthInterceptor;
import com.boltie.chatservice.interceptors.MessageMetadataInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99) //Mandatory in order to bypass default authentication
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final AuthInterceptor authInterceptor;
    private final MessageMetadataInterceptor messageMetadataInterceptor;

    @Value("${cors.allowed.origins}")
    private String allowedOrigins;

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(this.authInterceptor, this.messageMetadataInterceptor);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws")
                .setAllowedOrigins(allowedOrigins)
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
