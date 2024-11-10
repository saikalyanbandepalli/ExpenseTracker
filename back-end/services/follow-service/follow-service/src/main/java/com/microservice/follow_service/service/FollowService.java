package com.microservice.follow_service.service;


import com.microservice.follow_service.model.Follow;
import com.microservice.follow_service.producer.MessageProducer;
import com.microservice.follow_service.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private MessageProducer messageProducer;

    // Method to follow a user
    public String followUser(Long followerId, Long followingId) {
        // Check if the follower is not already following the followee
        if (followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            return "You are already following this user.";
        }

        // Create a new Follow object
        Follow follow = new Follow(followerId, followingId);

        // Save to the repository (this could be a database table)
        followRepository.save(follow);

        // Send a message to RabbitMQ to notify the notification service about the follow event
        String followMessage = "User " + followerId + " followed user " + followingId;
        messageProducer.sendFollowNotification(followMessage);

        return "User " + followerId + " successfully followed user " + followingId;
    }
}
