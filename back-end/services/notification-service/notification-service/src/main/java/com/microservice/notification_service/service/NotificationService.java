package com.microservice.notification_service.service;

import com.microservice.notification_service.Repository.NotificationRepository;
import com.microservice.notification_service.model.Notification;
import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmailService emailService;


    public Notification createNotification(String recipient, String message, String type) {
        Notification notification = new Notification();
        notification.setRecipient(recipient);
        notification.setMessage(message);
        notification.setType(type);
        notification.setTimestamp(LocalDateTime.now());
        notification.setRead(false);
        return notificationRepository.save(notification);
    }

    public void markAsRead(Long id) {
        notificationRepository.findById(id).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }

    public List<Notification> getAllNotifications(){
       return notificationRepository.findAll();
    }

    @RabbitListener(queues = "notificationQueue")
    public void receiveNotificationMessage(String message) {
        // Parse the message and create a notification
        // You may want to extract the user email and message details
        // For example: Extracting user email from the message
        String recipientEmail = "user-email@example.com"; // Extract from the message or database
        String subject = "Post Notification";
        String content = message; // The message content (post action: create/update/delete)

        try {
            // Send email
            emailService.sendEmail(recipientEmail, subject, content);

            // Optionally, save the notification to the database
            Notification notification = new Notification();
            notification.setRecipient(recipientEmail);
            notification.setMessage(content);
            notification.setType("Post Notification");
            notificationRepository.save(notification);

        } catch (MessagingException e) {
            // Handle email sending failure
            e.printStackTrace();
        }
    }
}

