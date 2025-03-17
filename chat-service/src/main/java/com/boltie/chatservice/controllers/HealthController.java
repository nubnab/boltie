package com.boltie.chatservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Chat service is up and running");
    }

    @GetMapping("/test")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("works");
    }

}
