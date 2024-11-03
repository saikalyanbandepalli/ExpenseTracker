package com.microservice.notification_service.controller;

import com.microservice.notification_service.model.Notification;
import com.microservice.notification_service.producer.MessageProducer;
import com.microservice.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MessageProducer messageProducer;

    @PostMapping("/send")
    public Notification sendNotification(@RequestParam String recipient,
                                         @RequestParam String message,
                                         @RequestParam String type) {
        // Create the notification and save it to the database
        Notification notification = notificationService.createNotification(recipient, message, type);

        // Send the notification message to RabbitMQ using MessageProducer
       // messageProducer.sendMessage("notificationExchange", "notificationQueue", message);
        messageProducer.sendMessage("notificationExchange", "notificationQueue", message);
        // Log the sent message for debugging purposes
        System.out.println("Sent notification to RabbitMQ: " + message);

        return notification;
    }

    @GetMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }
}
