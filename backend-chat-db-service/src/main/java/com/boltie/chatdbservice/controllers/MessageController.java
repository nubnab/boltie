package com.boltie.chatdbservice.controllers;

import com.boltie.chatdbservice.dto.MessageDto;
import com.boltie.chatdbservice.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/chat/{id}")
    public ResponseEntity<List<MessageDto>> getRecentMessages(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.getRecentMessages(id));
    }
}
