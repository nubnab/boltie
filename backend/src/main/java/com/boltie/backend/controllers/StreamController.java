package com.boltie.backend.controllers;

import com.boltie.backend.config.UserAuthProvider;
import com.boltie.backend.dto.StreamDto;
import com.boltie.backend.dto.UserDto;
import com.boltie.backend.services.StreamService;
import com.boltie.backend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StreamController {

    private final StreamService streamService;
    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    public StreamController(StreamService streamService, UserService userService, UserAuthProvider userAuthProvider) {
        this.streamService = streamService;
        this.userService = userService;
        this.userAuthProvider = userAuthProvider;
    }


    @GetMapping("/streams/{username}")
    public ResponseEntity<StreamDto> getStreamsByUsername(@PathVariable String username) {
        UserDto userDto = userService.findByUsername(username);
        StreamDto streamDetails = streamService.getStreamDetails(userDto.getId());
        streamDetails.setUsername(username);
        return ResponseEntity.ok(streamDetails);
    }

    @GetMapping("/streams/key")
    public ResponseEntity<Map<String, String>> getStreamKey(HttpServletRequest request) {
        String username = userAuthProvider.getUsernameFromRequest(request);
        Long id = userService.findByUsername(username).getId();

        return ResponseEntity.ok(streamService.getStreamKey(id));
    }

    @GetMapping("/streams")
    public ResponseEntity<List<StreamDto>> getStreams() {
        return ResponseEntity.ok(streamService.getAllStreams());
    }



}
