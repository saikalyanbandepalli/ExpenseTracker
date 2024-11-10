package com.microservice.follow_service.controller;

//import com.microservice.follow_service.DTO.UserDTO;
import com.microservice.follow_service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
    //package com.microservice.follow_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    @Autowired
    private RestTemplate restTemplate;

    private final String userServiceUrl = "http://user-service/api/users";


    @Autowired
    private RabbitTemplate rabbitTemplate;

    // Logic to follow a user
    @PostMapping("/{followerId}/{followeeId}")
    public String followUser(@PathVariable Long followerId, @PathVariable Long followeeId) {
        // Logic to follow a user (create the follow relationship in the database)

        // Prepare the message to send to the 'followQueue'
        String message = "Follower " + followerId + " follows Followee " + followeeId;

        // Send the message to the 'followQueue'
        rabbitTemplate.convertAndSend("followExchange", "followQueue", message); // Send to the followQueue
        return "User followed successfully";
    }


    // URL of user service


    @GetMapping("/{userId}/followers")
    public List<UserDTO> getFollowers(@PathVariable Long userId) {
        // Call user service to get followers
        String url = userServiceUrl + "/" + userId + "/followers";
        UserDTO[] users = restTemplate.getForObject(url, UserDTO[].class);
        return users != null ? List.of(users) : new ArrayList<>();
    }
}

