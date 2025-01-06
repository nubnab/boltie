package com.boltie.backend.repositories;

import com.boltie.backend.entities.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamRepository extends JpaRepository<Stream, Long> {

}
