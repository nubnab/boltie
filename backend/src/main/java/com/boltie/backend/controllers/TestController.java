package com.boltie.backend.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class TestController {


    private final RestTemplate chatApiRestTemplate;

    public TestController(@Qualifier("chatApiRestTemplate") RestTemplate chatApiRestTemplate) {
        this.chatApiRestTemplate = chatApiRestTemplate;
    }

    @GetMapping("/videos")
    public ResponseEntity<List<String>> test() {
        return ResponseEntity.ok(List.of("test1", "test2", "test3"));
    }

    @GetMapping("/chat")
    public ResponseEntity<String> test2() {
        return chatApiRestTemplate
                .getForEntity("http://backend-chat:8080/health", String.class);
    }
}
