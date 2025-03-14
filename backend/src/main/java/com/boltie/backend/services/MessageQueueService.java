package com.boltie.backend.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageQueueService {

    private final RabbitTemplate rabbitTemplate;

    public MessageQueueService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishChatCreationRequest(Long userId) {
        rabbitTemplate.convertAndSend("chatExchange",
                "chatCreation", userId);
    }

}
