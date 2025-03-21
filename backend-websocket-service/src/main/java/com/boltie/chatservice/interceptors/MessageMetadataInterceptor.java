package com.boltie.chatservice.interceptors;

import com.boltie.chatservice.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MessageMetadataInterceptor implements ChannelInterceptor {

        private final ObjectMapper objectMapper;

        @Override
        public Message<?> preSend(@NonNull Message<?> message,
                                  @NonNull MessageChannel channel) {

            StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

            if (accessor != null && StompCommand.SEND.equals(accessor.getCommand())) {
                CustomUserDetails authenticatedUser = getAuthenticatedUser(accessor);

                byte[] finalizedMessage = injectMessageMetadata(message, authenticatedUser);

                return MessageBuilder.createMessage(finalizedMessage, accessor.toMessageHeaders());
            }
            return message;
        }

        private CustomUserDetails getAuthenticatedUser(StompHeaderAccessor accessor) {
            Authentication authentication = (Authentication) accessor.getUser();

            if (authentication != null) {
                return (CustomUserDetails) authentication.getPrincipal();
            }
           throw new RuntimeException("Authentication is null");
        }

        private byte[] injectMessageMetadata(Message<?> message,
                                             CustomUserDetails authenticatedUser) {
            try {
                byte[] inputMessage = (byte[]) message.getPayload();
                Long chatRoomId = extractChatRoomId(message);

                MessageDto receivedMessage = objectMapper.readValue(inputMessage, MessageDto.class);

                MessageDto modifiedMessage = new MessageDto(
                        chatRoomId,
                        authenticatedUser.getUserId(),
                        authenticatedUser.getUsername(),
                        receivedMessage.content(),
                        OffsetDateTime.now(Clock.systemUTC())
                );

                return objectMapper.writeValueAsBytes(modifiedMessage);
            } catch (IOException e) {
                throw new RuntimeException("Message could not be parsed ", e);
            }
        }

        private Long extractChatRoomId(Message<?> message) {
            return Long.parseLong(Objects.requireNonNull(message
                            .getHeaders().get("simpDestination"))
                                .toString().substring(15));
        }

}
