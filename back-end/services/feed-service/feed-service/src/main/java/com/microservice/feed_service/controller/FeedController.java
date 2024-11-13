package com.microservice.feed_service.controller;

//package com.example.feedservice.controller;

import com.microservice.feed_service.DTO.postDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    @Autowired
    private RestTemplate restTemplate;

    private final String postServiceUrl = "http://localhost:8082/api/posts";



    @GetMapping("/getFeed")
    public ResponseEntity<List<postDTO>> getUserFeed(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        // Check if the Authorization header is provided
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // Token not provided or invalid format
        }

        // Extract token from the Authorization header (Assumes format "Bearer <token>")
        String token = authorizationHeader.replace("Bearer ", "");

        // Set up the headers for the request to the post service
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);  // Send token to the external service

        try {
            // Create an HttpEntity with the headers to send along with the request
            // Send the GET request and retrieve the list of postDTO
            List<postDTO> posts = restTemplate.exchange(
                            postServiceUrl, HttpMethod.GET, new HttpEntity<>(headers), List.class)
                    .getBody();

            // Return the response body as a list of postDTO
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            // Handle any errors that occur when calling the external service
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}


