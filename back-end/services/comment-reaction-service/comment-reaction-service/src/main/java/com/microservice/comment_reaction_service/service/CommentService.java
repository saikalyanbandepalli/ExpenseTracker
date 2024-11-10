package com.microservice.comment_reaction_service.service;

//package com.microservice.comment_reaction_service.service;

import com.microservice.comment_reaction_service.model.Comment;
import com.microservice.comment_reaction_service.model.Reaction;
import com.microservice.comment_reaction_service.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private MessageProducer messageProducer; // RabbitMQ producer for notifications

    public String addComment(Long userId, Comment comment) {
        comment.setUserId(userId); // Associate the comment with the user
        // Logic to save the comment to the database
        return "Comment added successfully";
    }

    public String reactToComment(Long postId, Long userId, Reaction reaction) {
        reaction.setUserId(userId); // Associate the reaction with the user
        // Logic to save the reaction to the database

        // Send a notification to the user who made the comment
        String notificationMessage = "User " + userId + " reacted to your comment on post " + postId;
        messageProducer.sendMessage("notificationExchange", "notificationQueue", notificationMessage);

        return "Reaction added successfully";
    }
}

