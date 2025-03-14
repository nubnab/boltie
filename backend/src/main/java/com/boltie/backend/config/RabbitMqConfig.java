package com.boltie.backend.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        AmqpAdmin amqpAdmin = new RabbitAdmin(connectionFactory);
        amqpAdmin.declareQueue(chatCreationQueue());
        return amqpAdmin;
    }

    @Bean
    public Queue chatCreationQueue() {
        return new Queue("chatCreationQueue", true);
    }

    @Bean
    public TopicExchange chatTopicExchange() {
        return new TopicExchange("chatExchange");
    }

    @Bean
    public Binding bindingChatCreationQueue(Queue chatCreationQueue,
                                            TopicExchange chatTopicExchange) {
        return BindingBuilder.bind(chatCreationQueue).to(chatTopicExchange)
                .with("chatCreation");
    }

}
