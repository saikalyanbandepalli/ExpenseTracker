package com.microservice.notification_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    @Autowired
    private NotificationService notificationService;

   @RabbitListener(queues = "notificationQueue")
    public void receiveMessage(String message) {
        System.out.println("Received message from RabbitMQ: " + message);
        try {
            notificationService.createNotification("user@example.com", message, "EMAIL");
        } catch (Exception e) {
            System.err.println("Failed to process message: " + message);
            e.printStackTrace();
        }
    }

}

