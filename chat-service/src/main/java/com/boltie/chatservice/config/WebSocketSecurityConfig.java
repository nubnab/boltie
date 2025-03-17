package com.boltie.chatservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig
        extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                //.nullDestMatcher().authenticated()
                .simpSubscribeDestMatchers("/topic/**").permitAll()
                .simpDestMatchers("/app/**").hasRole("USER")
                .anyMessage().permitAll();

    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
