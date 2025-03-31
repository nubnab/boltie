package com.boltie.backend.services;

import com.boltie.backend.entities.Recording;
import com.boltie.backend.entities.RecordingWatchHistory;
import com.boltie.backend.entities.User;
import com.boltie.backend.repositories.RecordingWatchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordingWatchHistoryService {

    private final RecordingWatchHistoryRepository recordingWatchHistoryRepository;

    public void recordView(User viewer, Recording recording) {
        recordingWatchHistoryRepository.save(RecordingWatchHistory.builder()
                .viewedBy(viewer)
                .recording(recording)
                .build());
    }

}
