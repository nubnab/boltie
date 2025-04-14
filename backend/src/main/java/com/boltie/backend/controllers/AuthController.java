package com.boltie.backend.controllers;

import dto.RegisterDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class AuthController {

    @PostMapping("/auth/register")
    ResponseEntity<RegisterDto> addUser(@Valid @RequestBody RegisterDto registerDto) {
        return ResponseEntity.created(URI.create("/auth/register")).body(registerDto);
    }


}
