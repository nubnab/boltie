package com.boltie.backend.facades;

import com.boltie.backend.config.UserAuthProvider;
import com.boltie.backend.dto.StreamDto;
import com.boltie.backend.dto.StreamKeyDto;
import com.boltie.backend.dto.StreamTitleDto;
import com.boltie.backend.exceptions.AppException;
import com.boltie.backend.services.RecordingService;
import com.boltie.backend.services.StreamService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StreamFacadeService {

    private final UserAuthProvider userAuthProvider;
    private final StreamService streamService;
    private final RecordingService recordingService;

    public StreamKeyDto getStreamKeyFromRequest(HttpServletRequest request) {
        String username = userAuthProvider.getUsernameFromRequest(request);

        return streamService.getStreamKey(username);
    }

    public List<StreamDto> getAllStreams() throws JsonProcessingException {
        return streamService.getAllLiveStreams();
    }

    public StreamTitleDto editStreamTitle(StreamTitleDto streamTitleDto, HttpServletRequest request) {
        String username = userAuthProvider.getUsernameFromRequest(request);
        String newTitle = streamTitleDto.title();

        if (username != null) {
            if(streamService.userIsStreaming(username)) {
                recordingService.editCurrentRecordingTitle(newTitle, username);
            }
            return streamService.editStreamTitle(newTitle, username);
        }

        throw new AppException("User  not found.", HttpStatus.NOT_FOUND);
    }

    public StreamDto getStreamDetails(String username) {
        return streamService.getStreamDetails(username);
    }



}
