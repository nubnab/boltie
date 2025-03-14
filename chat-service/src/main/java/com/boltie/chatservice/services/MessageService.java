package com.boltie.chatservice.services;

import com.boltie.chatservice.entities.Message;
import com.boltie.chatservice.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public List<Message> getMessages(Long chatId) {
        Optional<List<Message>> first5ByChatRoomIdOrderByIdDesc =
                messageRepository.findFirst5ByChatRoom_IdOrderByIdDesc(chatId);

        return first5ByChatRoomIdOrderByIdDesc.orElse(null);
    }

}
