package com.boltie.chatservice.services;

import com.boltie.chatservice.entities.ChatRoom;
import com.boltie.chatservice.repositories.ChatRoomRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @RabbitListener(queues = "chatCreationQueue")
    public void handleChatCreationRequest(Long chatOwnerId) {
        createChatRoom(chatOwnerId);
        System.out.println("Created chat room for user: " + chatOwnerId);
    }

    private void createChatRoom(Long chatOwnerId) {
        chatRoomRepository.save(ChatRoom
                .builder()
                .chatOwnerId(chatOwnerId)
                .messages(new ArrayList<>())
                .build());
    }

    public ChatRoom findById(Long id) {
        return chatRoomRepository.findById(id).orElse(null);
    }

}
