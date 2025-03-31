package com.boltie.backend.controllers;

import com.boltie.backend.dto.RecordingDto;
import com.boltie.backend.facades.RecordingFacadeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HistoryController {

    private final RecordingFacadeService recordingFacadeService;

    @GetMapping("/history")
    public ResponseEntity<List<RecordingDto>> getHistory(HttpServletRequest request) {
        return ResponseEntity.ok(recordingFacadeService.getRecordingHistory(request));
    }

}
