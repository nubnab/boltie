package com.boltie.chatdbservice.repositories;

import com.boltie.chatdbservice.entities.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findFirstByChatOwnerId(Long chatOwnerId);

}
