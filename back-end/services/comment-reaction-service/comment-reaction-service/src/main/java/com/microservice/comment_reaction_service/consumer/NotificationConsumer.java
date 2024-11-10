package com.microservice.comment_reaction_service.consumer;

//package com.microservice.comment_reaction_service.consumer;

import com.microservice.comment_reaction_service.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.json.JSONObject;

@Component
public class NotificationConsumer {

    @Autowired
    private EmailService emailService; // Service to handle email sending

    @RabbitListener(queues = "commentReactionQueue")
    public void receiveMessage(String message) {
        System.out.println("Received notification from RabbitMQ: " + message);

        // Process the message to extract user info and notification content
        // Assuming message contains details like "User 123 reacted to your comment on post 456"
        String recipientEmail = extractRecipientEmail(message);
        String subject = "New Reaction on Your Comment";
        String emailContent = message;

        // Send email notification
        emailService.sendEmail(recipientEmail, subject, emailContent);
    }

    // Example method to extract recipient email from message (based on your message format)
    private String extractRecipientEmail(String message) {
        try {
            JSONObject jsonMessage = new JSONObject(message);
            return jsonMessage.getString("recipientEmail"); // Extracts the email
        } catch (Exception e) {
            System.out.println("Error parsing message: " + e.getMessage());
            return null; // Handle errors appropriately in production
        }
    }
}

