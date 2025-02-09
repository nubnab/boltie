package com.boltie.backend.controllers;

import com.boltie.backend.dto.RecordingDto;
import com.boltie.backend.dto.UserDto;
import com.boltie.backend.services.RecordingService;
import com.boltie.backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecordingController {

    private final UserService userService;
    private final RecordingService recordingService;

    public RecordingController(UserService userService,
                               RecordingService recordingService) {
        this.userService = userService;
        this.recordingService = recordingService;
    }

    @GetMapping("/recordings/{username}")
    public ResponseEntity<List<RecordingDto>> getRecordings(@PathVariable String username) {
        UserDto user = userService.findByUsername(username);
        return ResponseEntity.ok(recordingService.fetchRecordings(user.getId()));
    }

    @GetMapping("/recordings/{username}/{recordingId}")
    public ResponseEntity<RecordingDto> getRecording(@PathVariable String username,
                                                     @PathVariable Long recordingId) {
        UserDto user = userService.findByUsername(username);
        Long userId = user.getId();
        return ResponseEntity.ok(recordingService.fetchRecording(userId, recordingId));
    }

}
