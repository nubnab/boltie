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
        return new Queue("chat.creation.queue", true);
    }

    @Bean
    public TopicExchange chatTopicExchange() {
        return new TopicExchange("chat.creation.exchange");
    }

    @Bean
    public Binding bindingChatCreationQueue(Queue chatCreationQueue,
                                            TopicExchange chatTopicExchange) {
        return BindingBuilder.bind(chatCreationQueue).to(chatTopicExchange)
                .with("chat.creation");
    }

    @Bean
    public TopicExchange chatExchange() {
        return new TopicExchange("chat.exchange");
    }

    @Bean
    public Queue chatQueue() {
        return new Queue("chat.queue");
    }

    @Bean
    public Binding bindingChatQueue(Queue chatQueue,
                                    TopicExchange chatExchange) {
        return BindingBuilder
                .bind(chatQueue)
                .to(chatExchange)
                .with("chat.#");
    }

}
