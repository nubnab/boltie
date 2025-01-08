package com.boltie.backend.controllers;

import com.boltie.backend.dto.StreamDto;
import com.boltie.backend.dto.UserDto;
import com.boltie.backend.services.StreamService;
import com.boltie.backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StreamController {

    private final StreamService streamService;
    private final UserService userService;

    public StreamController(StreamService streamService, UserService userService) {
        this.streamService = streamService;
        this.userService = userService;
    }


    @GetMapping("/streams/{username}")
    public ResponseEntity<StreamDto> getStreamsByUsername(@PathVariable String username) {
        UserDto userDto = userService.findByUsername(username);
        return ResponseEntity.ok(streamService.getStreamDetails(userDto.getId()));

    }

    @GetMapping("/streams")
    public ResponseEntity<List<StreamDto>> getStreams() {
        return ResponseEntity.ok(streamService.getAllStreams());
    }

}
