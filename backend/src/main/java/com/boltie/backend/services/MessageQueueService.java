package com.boltie.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageQueueService {

    private final RabbitTemplate rabbitTemplate;

    public MessageQueueService(RabbitTemplate rabbitTemplate,
                               MessageConverter jsonMessageConverter) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
    }

    public void publishChatCreationRequest(Long userId) {
        rabbitTemplate.convertAndSend("chat.creation.exchange",
                "chat.creation", userId);
        log.info("Published chat creation request for user {}", userId);
    }
}
