package com.boltie.backend.services;

import com.boltie.backend.entities.Recording;
import com.boltie.backend.entities.User;
import com.boltie.backend.entities.WatchLater;
import com.boltie.backend.repositories.WatchLaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WatchLaterService {

    private final WatchLaterRepository watchLaterRepository;

    public void addToWatchLater(User viewer, Recording recording) {
        if(!isDuplicate(viewer, recording)) {
            watchLaterRepository.save(WatchLater.builder()
                    .savedBy(viewer)
                    .recording(recording)
                    .build());
        }
    }

    private boolean isDuplicate(User viewer, Recording recording) {
        return watchLaterRepository.existsBySavedByAndRecording(viewer, recording);
    }


}
