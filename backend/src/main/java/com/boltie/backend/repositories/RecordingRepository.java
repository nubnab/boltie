package com.boltie.backend.repositories;

import com.boltie.backend.entities.Recording;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordingRepository extends JpaRepository<Recording, Long> {

    Optional<List<Recording>> findAllByUserId(Long userId);


}
