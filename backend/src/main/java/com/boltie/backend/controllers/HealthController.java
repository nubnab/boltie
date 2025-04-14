package com.boltie.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    private ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("UP");
    }

}
