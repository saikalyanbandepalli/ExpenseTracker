package com.microservice.follow_service.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    // Send follow notification message to a specific queue
    public void sendFollowNotification(String message) {
        // Send to the followNotificationQueue
        amqpTemplate.convertAndSend("notificationExchange", "followNotificationQueue", message);
        System.out.println("Follow notification sent to RabbitMQ: " + message);
    }
}
