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
        amqpAdmin.declareQueue(chatCreationQueue()); //could possibly be omitted
        return amqpAdmin;
    }

    @Bean
    public Queue chatCreationQueue() {
        return new Queue("chat.creation.queue", true);
    }

    @Bean
    public TopicExchange chatTopicExchange() {
        return new TopicExchange("chat.creation.exchange");
    }

    @Bean
    public Binding bindingChatCreationQueue(Queue chatCreationQueue,
                                            TopicExchange chatTopicExchange) {
        return BindingBuilder
                .bind(chatCreationQueue)
                .to(chatTopicExchange)
                .with("chat.creation");
    }

    @Bean
    public TopicExchange chatExchange() {
        return new TopicExchange("chat.message.exchange");
    }

    @Bean
    public Queue chatMessageQueue() {
        return new Queue("chat.message.queue", true);
    }

    @Bean
    public Binding bindingChatQueue(Queue chatMessageQueue,
                                    TopicExchange chatExchange) {
        return BindingBuilder
                .bind(chatMessageQueue)
                .to(chatExchange)
                .with("chat.message");
    }
}
