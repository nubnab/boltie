package com.boltie.backend.repositories;

import com.boltie.backend.entities.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StreamRepository extends JpaRepository<Stream, Long> {

    Optional<Stream> findFirstByUser_Username(String username);



}
