package com.boltie.backend.repositories;

import com.boltie.backend.entities.Recording;
import com.boltie.backend.entities.User;
import com.boltie.backend.entities.WatchLater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchLaterRepository extends JpaRepository<WatchLater, Long> {

    boolean existsBySavedByAndRecording(User savedBy, Recording recording);

}
