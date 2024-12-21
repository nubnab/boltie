package com.boltie.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @GetMapping("/videos")
    public ResponseEntity<List<String>> test() {
        return ResponseEntity.ok(List.of("test1", "test2", "test3"));
    }
}
