package com.boltie.backend.controllers;

import com.boltie.backend.dto.RecordingDto;
import com.boltie.backend.services.RecordingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecordingController {

    private final RecordingService recordingService;

    @GetMapping("/recordings/{username}")
    public ResponseEntity<List<RecordingDto>> getRecordings(@PathVariable String username) {
        return ResponseEntity.ok(recordingService.getRecordings(username));
    }

    @GetMapping("/recordings/{username}/{recordingId}")
    public ResponseEntity<RecordingDto> getRecording(@PathVariable String username,
                                                     @PathVariable Long recordingId) {

        return ResponseEntity.ok(recordingService.getRecording(username, recordingId));
    }

}
