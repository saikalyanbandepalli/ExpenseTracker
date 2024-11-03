package com.microservice.follow_service.controller;

import com.microservice.follow_service.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    @Autowired
    private RestTemplate restTemplate;

    private final String userServiceUrl = "http://user-service/api/users"; // URL of user service

    @PostMapping("/{followerId}/{followeeId}")
    public String followUser(@PathVariable Long followerId, @PathVariable Long followeeId) {
        // Logic to follow a user
        return "User followed successfully";
    }

    @GetMapping("/{userId}/followers")
    public List<UserDTO> getFollowers(@PathVariable Long userId) {
        // Call user service to get followers
        String url = userServiceUrl + "/" + userId + "/followers";
        UserDTO[] users = restTemplate.getForObject(url, UserDTO[].class);
        return users != null ? List.of(users) : new ArrayList<>();
    }
}
