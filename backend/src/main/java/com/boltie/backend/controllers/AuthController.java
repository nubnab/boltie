package com.boltie.backend.controllers;

import com.boltie.backend.dto.LoginDto;
import com.boltie.backend.dto.RegisterDto;
import com.boltie.backend.dto.UserDto;
import com.boltie.backend.dto.UsernameChangeDto;
import com.boltie.backend.facades.UserFacadeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserFacadeService userFacadeService;

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refresh_token");

        if(refreshToken == null || refreshToken.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            UserDto userDto = userFacadeService.validateRefreshToken(refreshToken);
            String newAuthToken = userFacadeService.createToken(userDto);
            Map<String, String> response = new HashMap<>();

            response.put("authToken", newAuthToken);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserDto user = userFacadeService.login(loginDto);

        user.setToken(userFacadeService.createToken(user));
        user.setRefreshToken(userFacadeService.createRefreshToken(user));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid  @RequestBody RegisterDto registerDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserDto user = userFacadeService.registerUser(registerDto);

        user.setToken(userFacadeService.createToken(user));
        user.setRefreshToken(userFacadeService.createRefreshToken(user));

        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }

    @PatchMapping("/edit-username")
    public ResponseEntity<?> changeUsername(@RequestBody UsernameChangeDto usernameChangeDto,
                                            HttpServletRequest request) {
        userFacadeService.changeUsername(usernameChangeDto, request);
        return ResponseEntity.ok().build();
    }
}
