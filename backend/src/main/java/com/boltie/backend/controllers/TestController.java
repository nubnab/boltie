package com.boltie.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<List<String>> test() {
        return ResponseEntity.ok(Arrays.asList("hello", "world"));
    }
}
