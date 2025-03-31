package com.boltie.backend.controllers;

import com.boltie.backend.dto.RecordingDto;
import com.boltie.backend.facades.RecordingFacadeService;
import com.boltie.backend.services.RecordingService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final RecordingFacadeService recordingFacadeService;

    @GetMapping("/recordings/{username}")
    public ResponseEntity<List<RecordingDto>> getRecordings(@PathVariable String username) {
        return ResponseEntity.ok(recordingService.getRecordings(username));
    }

    @GetMapping("/recordings/{username}/{recordingId}")
    public ResponseEntity<RecordingDto> getRecording(@PathVariable String username,
                                                     @PathVariable Long recordingId,
                                                     HttpServletRequest request) {

        return ResponseEntity.ok(recordingFacadeService.getRecordingAndLogHistory(username, recordingId, request));
    }
}
