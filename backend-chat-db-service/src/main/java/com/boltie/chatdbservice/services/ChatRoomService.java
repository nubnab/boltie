package com.boltie.chatdbservice.services;

import com.boltie.chatdbservice.entities.ChatRoom;
import com.boltie.chatdbservice.repositories.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @RabbitListener(queues = "chat.creation.queue")
    public void chatRoomCreationListener(Long userId) {
        createChatRoom(userId);
    }

    private void createChatRoom(Long userId) {

        Optional<ChatRoom> existingChatRoom =
                chatRoomRepository.findFirstByChatOwnerId(userId);
        if (existingChatRoom.isPresent()) {
            log.error("Chat room with ID: {} already exists.", userId);
            return;
        }

        chatRoomRepository.save(ChatRoom
                .builder()
                .chatOwnerId(userId)
                .messages(new ArrayList<>())
                .build());

        log.info("Chat room with ID: {} created.", userId);
    }
}
