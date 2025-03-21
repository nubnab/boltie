package com.boltie.chatdbservice.repositories;

import com.boltie.chatdbservice.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<List<Message>> findFirst50ByChatRoom_IdOrderByIdDesc(Long chatRoomId);
}