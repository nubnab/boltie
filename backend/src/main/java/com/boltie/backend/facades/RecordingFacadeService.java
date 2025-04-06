package com.boltie.backend.facades;

import com.boltie.backend.config.UserAuthProvider;
import com.boltie.backend.dto.RecordingDto;
import com.boltie.backend.entities.Recording;
import com.boltie.backend.entities.User;
import com.boltie.backend.mappers.RecordingMapper;
import com.boltie.backend.services.RecordingService;
import com.boltie.backend.services.RecordingWatchHistoryService;
import com.boltie.backend.services.UserService;
import com.boltie.backend.services.WatchLaterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordingFacadeService {

    private final RecordingService recordingService;
    private final RecordingWatchHistoryService recordingWatchHistoryService;
    private final UserAuthProvider userAuthProvider;
    private final UserService userService;
    private final RecordingMapper recordingMapper;
    private final WatchLaterService watchLaterService;


    public RecordingDto getRecordingAndLogHistory(String username,
                                     Integer userRecordingTrackingId,
                                     HttpServletRequest request) {

        String requesterUsername = userAuthProvider.getUsernameFromRequest(request);
        Recording recording = recordingService.getRecordingEntity(username, userRecordingTrackingId);

        if(requesterUsername != null) {
            User viewer = userService.getUserByUsername(requesterUsername);

            recordingWatchHistoryService.recordView(viewer, recording);
        }
        return recordingMapper.toRecordingDto(recording);
    }

    public List<RecordingDto> getRecordingHistory(HttpServletRequest request) {
        String requesterUsername = userAuthProvider.getUsernameFromRequest(request);

        if(requesterUsername != null) {
            User viewer = userService.getUserByUsername(requesterUsername);
            List<RecordingDto> watchHistory = new ArrayList<>();

            viewer.getWatchHistory().forEach(e -> watchHistory
                    .add(recordingMapper.toRecordingDto(e.getRecording())));

            return watchHistory;
        }
        return null;
    }

    public void saveWatchLaterRecording(String username,
                                        Integer userRecordingTrackingId,
                                        HttpServletRequest request) {
        String requesterUsername = userAuthProvider.getUsernameFromRequest(request);

        if(requesterUsername != null) {
            User viewer = userService.getUserByUsername(requesterUsername);
            Recording recordingEntity = recordingService.getRecordingEntity(username, userRecordingTrackingId);

            watchLaterService.addToWatchLater(viewer, recordingEntity);
        }

    }

    public List<RecordingDto> getWatchLaterHistory(HttpServletRequest request) {
        String requesterUsername = userAuthProvider.getUsernameFromRequest(request);

        if(requesterUsername != null) {
            User viewer = userService.getUserByUsername(requesterUsername);
            List<RecordingDto> watchLaterHistory = new ArrayList<>();

            viewer.getWatchLaterList().forEach(e -> watchLaterHistory
                .add(recordingMapper.toRecordingDto(e.getRecording())));
            return watchLaterHistory;
        }
        return null;
    }

}
