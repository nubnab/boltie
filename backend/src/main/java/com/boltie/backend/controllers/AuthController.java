package com.boltie.backend.controllers;

import com.auth0.jwt.algorithms.Algorithm;
import com.boltie.backend.config.UserAuthProvider;
import com.boltie.backend.dto.LoginDto;
import com.boltie.backend.dto.RegisterDto;
import com.boltie.backend.dto.UserDto;
import com.boltie.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    public AuthController(UserService userService, UserAuthProvider userAuthProvider) {
        this.userService = userService;
        this.userAuthProvider = userAuthProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserDto user = userService.login(loginDto);

        user.setToken(userAuthProvider.createToken(user));
        user.setRefreshToken(userAuthProvider.createRefreshToken(user));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refresh_token");

        if(refreshToken == null || refreshToken.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            UserDto userDto = userAuthProvider.validateRefreshToken(refreshToken);

            String newAuthToken = userAuthProvider.createToken(userDto);

            Map<String, String> response = new HashMap<>();

            response.put("auth_token", newAuthToken);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid  @RequestBody RegisterDto registerDto,
                                            BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserDto user = userService.registerUser(registerDto);

        user.setToken(userAuthProvider.createToken(user));
        user.setRefreshToken(userAuthProvider.createRefreshToken(user));

        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }

}
