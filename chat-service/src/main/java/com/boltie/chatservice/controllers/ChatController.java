package com.boltie.chatservice.controllers;

import com.boltie.chatservice.dto.MessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final RabbitTemplate rabbitTemplate;

    public ChatController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @MessageMapping("/chat/{chatId}/sendMessage")
    @SendTo("/topic/chat/{chatId}")
    public MessageDto sendMessage(@DestinationVariable String chatId,
                                  @Payload MessageDto message) {

        rabbitTemplate.convertAndSend("chat.exchange","chat." + chatId, message);

        return message;
    }

}
