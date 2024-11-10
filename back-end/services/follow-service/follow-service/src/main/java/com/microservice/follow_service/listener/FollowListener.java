package com.microservice.follow_service.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FollowListener {

    @Autowired
    private RestTemplate restTemplate;

    // Define the URL of the Notification service (ensure this is correct for your system)
    private final String notificationServiceUrl = "http://notification-service/api/notifications/send-follow-notification";

    // Listen to the 'followQueue' for follow events
    @RabbitListener(queues = "followQueue")
    public void receiveFollowQueueMessage(String message) {
        // Log the received message for debugging
        System.out.println("Received follow message: " + message);

        // Send the email notification to the followee
        sendFollowNotification(message);
    }

    // This method is responsible for sending the follow notification to the followee via the notification service
    private void sendFollowNotification(String message) {
        // Parse the message to get follower and followee information (you can improve this as per your message format)
        String[] parts = message.split(" ");
        String followerId = parts[1];
        String followeeId = parts[3];

        // Build the notification payload (could be extended with more details)
        String notificationPayload = buildNotificationPayload(followerId, followeeId);

        // Send the request to NotificationService (via REST API) to send the email
        restTemplate.postForObject(notificationServiceUrl, notificationPayload, String.class);
    }

    // Method to build the payload for the notification service
    private String buildNotificationPayload(String followerId, String followeeId) {
        return "{\"followerId\": \"" + followerId + "\", \"followeeId\": \"" + followeeId + "\"}";
    }
}
