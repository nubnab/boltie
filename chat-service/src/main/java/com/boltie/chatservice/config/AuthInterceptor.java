package com.boltie.chatservice.config;

import lombok.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthInterceptor implements ChannelInterceptor {

    private final UserAuthProvider userAuthProvider;

    public AuthInterceptor(UserAuthProvider userAuthProvider) {
        this.userAuthProvider = userAuthProvider;
    }

    @Override
    public Message<?> preSend(@NonNull Message<?> message,
                              @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        assert accessor != null;

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String token = accessor.getFirstNativeHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);

                //TODO: Handle token expiry
                if(token.contains("null")) {
                    return message;
                }

                Authentication authentication = userAuthProvider.authenticateUser(token);
                accessor.setUser(authentication);
            }
        }

        return message;
    }
}
