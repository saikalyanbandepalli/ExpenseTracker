package com.microservice.follow_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // Define the 'followQueue' which will handle follow notifications
    @Bean
    public Queue followQueue() {
        return new Queue("followQueue", true); // Create a durable queue
    }
}
