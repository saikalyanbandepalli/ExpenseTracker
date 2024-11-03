package com.microservice.feed_service.controller;

//package com.example.feedservice.controller;

import com.microservice.feed_service.DTO.postDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    @Autowired
    private RestTemplate restTemplate;

    private final String postServiceUrl = "http://post-service/api/posts";

    @GetMapping("/{userId}")
    public List<postDTO> getUserFeed(@PathVariable Long userId) {
        // Logic to get user feed
        String url = postServiceUrl + "/" + userId;
        postDTO[] posts = restTemplate.getForObject(url,postDTO[].class);
        return posts != null ? List.of(posts) : new ArrayList<>();
    }
}

