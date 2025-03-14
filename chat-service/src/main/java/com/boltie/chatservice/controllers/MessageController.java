package com.boltie.chatservice.controllers;

import com.boltie.chatservice.dto.MessageDto;
import com.boltie.chatservice.entities.Message;
import com.boltie.chatservice.mappers.MessageMapper;
import com.boltie.chatservice.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    public MessageController(MessageService messageService,
                             MessageMapper messageMapper) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
    }

    @GetMapping("/chat/{id}")
    public ResponseEntity<List<MessageDto>> getMessages(@PathVariable Long id) {
        List<Message> messages = messageService.getMessages(id);

        List<MessageDto> conMessages = new ArrayList<>();

        for (Message message : messages) {
            MessageDto messageDto = messageMapper.toMessageDto(message);
            conMessages.add(messageDto);
        }


        return ResponseEntity.ok(conMessages);
    }


    @PostMapping("/post-message/{chatId}")
    public void create(@PathVariable Long chatId) {
        Message message = new Message();
        message.setId(chatId);
    }
}
