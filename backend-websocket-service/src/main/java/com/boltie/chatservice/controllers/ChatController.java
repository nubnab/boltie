package com.boltie.chatservice.controllers;

import com.boltie.chatservice.dto.MessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final RabbitTemplate rabbitTemplate;

    public ChatController(RabbitTemplate rabbitTemplate,
                          MessageConverter jsonMessageConverter) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setMessageConverter(jsonMessageConverter);
    }

    @MessageMapping("/chat/send/{chatId}")
    @SendTo("/topic/chat/{chatId}")
    public MessageDto sendMessage(@Payload MessageDto message) {
        rabbitTemplate.convertAndSend("chat.message.exchange",
                "chat.message", message);

        return message;
    }
}
