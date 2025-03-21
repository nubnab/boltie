package com.boltie.chatservice.interceptors;

import com.boltie.chatservice.config.AuthProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements ChannelInterceptor {

    private final AuthProvider authProvider;

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

                accessor.setUser(authProvider.authenticateUser(token));
            }
        }
        return message;
    }
}
