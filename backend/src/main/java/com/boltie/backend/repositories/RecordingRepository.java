package com.boltie.backend.repositories;

import com.boltie.backend.entities.Recording;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordingRepository extends JpaRepository<Recording, Long> {

    Optional<List<Recording>> findAllByUser_Username(String userName);

    Optional<Recording> findFirstByUser_UsernameOrderByIdDesc(String username);

    Optional<Recording> findRecordingByUser_UsernameAndUserRecordingTrackingId(String username, Integer id);


}
