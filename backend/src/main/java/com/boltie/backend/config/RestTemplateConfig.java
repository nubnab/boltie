package com.boltie.backend.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${oven.api.username}")
    private String username;

    @Value("${oven.api.password}")
    private String password;

    @Value("${chat.api.key}")
    private String CHAT_API_KEY;

    @Bean
    @Qualifier("streamApiRestTemplate")
    public RestTemplate streamApiRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .basicAuthentication(username, password)
                .build();
    }

    @Bean
    @Qualifier("chatApiRestTemplate")
    public RestTemplate chatApiRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .defaultHeader("X-API-KEY", CHAT_API_KEY)
                .build();
    }

}
