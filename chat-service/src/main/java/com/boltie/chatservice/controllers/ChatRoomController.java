package com.boltie.chatservice.controllers;

import com.boltie.chatservice.services.ChatRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @PostMapping("/create-chat/{chatOwnerId}")
    public ResponseEntity<?> createChat(@PathVariable Long chatOwnerId) {

        //chatRoomService.createChatRoom(chatOwnerId);

        return ResponseEntity.created(URI.create("/chat/" + chatOwnerId)).build();
    }

}
