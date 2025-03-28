package com.boltie.backend.controllers;

import com.boltie.backend.dto.StreamDto;
import com.boltie.backend.dto.StreamKeyDto;
import com.boltie.backend.dto.StreamTitleDto;
import com.boltie.backend.facades.StreamFacadeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StreamController {

    private final StreamFacadeService streamFacadeService;

    //TODO: look into the exception
    @GetMapping("/streams")
    public ResponseEntity<List<StreamDto>> getStreams() throws JsonProcessingException {
        return ResponseEntity.ok(streamFacadeService.getAllStreams());
    }

    @GetMapping("/streams/key")
    public ResponseEntity<StreamKeyDto> getStreamKey(HttpServletRequest request) {
        return ResponseEntity.ok(streamFacadeService.getStreamKeyFromRequest(request));
    }

    @GetMapping("/streams/{username}")
    public ResponseEntity<StreamDto> getStreamDetails(@PathVariable String username) {
        return ResponseEntity.ok(streamFacadeService.getStreamDetails(username));
    }


    @PatchMapping("/streams/edit-title")
    public ResponseEntity<StreamTitleDto> editTitle(@RequestBody StreamTitleDto streamTitleDto,
                                                    HttpServletRequest request) {
        //TODO: eliminate response body, request on frontend to fetch current streamTitle
        return ResponseEntity.ok(streamFacadeService.editStreamTitle(streamTitleDto, request));

    }


}
