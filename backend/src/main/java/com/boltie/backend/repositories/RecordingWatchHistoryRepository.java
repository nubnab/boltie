package com.boltie.backend.repositories;

import com.boltie.backend.entities.Recording;
import com.boltie.backend.entities.RecordingWatchHistory;
import com.boltie.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordingWatchHistoryRepository extends JpaRepository<RecordingWatchHistory, Long> {

    boolean existsByViewedByAndRecording(User viewedBy, Recording recording);

}
