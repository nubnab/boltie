package com.boltie.backend.repositories;

import com.boltie.backend.entities.Recording;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordingRepository extends JpaRepository<Recording, Long> {

    Optional<List<Recording>> findAllByUserId(Long userId);
    Optional<Recording> findFirstByUser_UsernameOrderByIdDesc(String username);

    //TODO: Simplify with JPA query
    @Query("SELECT e FROM Recording e WHERE e.user.id = :userId ORDER BY e.id ASC")
    List<Recording> findByUserId(@Param("userId") Long userId, Pageable pageable);

    default Recording findNthRecordingByUserId(Long userId, Long n) {
        Pageable pageable = PageRequest.of(n.intValue() - 1, 1);

        List<Recording> recordingList = findByUserId(userId, pageable);

        return recordingList.isEmpty() ? null : recordingList.getFirst();
    }




}
