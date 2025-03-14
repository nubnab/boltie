package com.boltie.chatservice.repositories;

import com.boltie.chatservice.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<List<Message>> findFirst5ByChatRoom_IdOrderByIdDesc(Long chatRoomId);


}
