package com.boltie.backend.controllers;

import com.boltie.backend.dto.RecordingDto;
import com.boltie.backend.facades.RecordingFacadeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WatchLaterController {

    private final RecordingFacadeService recordingFacadeService;

    @GetMapping("/watch-later")
    public ResponseEntity<List<RecordingDto>> getWatchLater(HttpServletRequest request) {
        return ResponseEntity.ok(recordingFacadeService.getWatchLaterHistory(request));
    }

    @PostMapping("/watch-later/{username}/{userRecordingTrackingId}")
    public ResponseEntity<?> addRecordingToWatchLater(@PathVariable String username,
                                         @PathVariable Integer userRecordingTrackingId,
                                         HttpServletRequest request) {
        recordingFacadeService.saveWatchLaterRecording(username, userRecordingTrackingId, request);
        return ResponseEntity.ok().build();
    }
}
