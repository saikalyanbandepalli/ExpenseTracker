package com.microservice.post_service.controller;

// com.example.postservice.controller;

import com.microservice.post_service.model.Post;
import com.microservice.post_service.repository.PostRepository;
import com.microservice.post_service.service.MessageProducer;
import com.microservice.post_service.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private MessageProducer messageProducer;

    // Create a new post
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestHeader("Authorization") String authorization, @RequestBody Post post) {
        String token = authorization.substring(7); // Extract token by removing "Bearer "
        Long userId = postService.validateAndGetUserIdFromToken(token); // Validate JWT and get userId
        post.setUserId(userId); // Set the userId for the post
        Post createdPost = postService.createPost(post);
        String notificationMessage = "Post created: " + post.getTitle();
        messageProducer.sendMessage("notificationExchange", "notificationQueue", notificationMessage);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    // Get a user's post by ID
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@RequestHeader("Authorization") String authorization, @PathVariable Long postId) {
        String token = authorization.substring(7); // Extract token
        Long userId = postService.validateAndGetUserIdFromToken(token); // Validate JWT and get userId
        Post post = postService.getPost(postId, userId); // Ensure the user owns the post
        return ResponseEntity.ok(post);
    }

    // Update a user's post
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@RequestHeader("Authorization") String authorization, @PathVariable Long postId, @RequestBody Post postDetails) {
        String token = authorization.substring(7); // Extract token
        Long userId = postService.validateAndGetUserIdFromToken(token); // Validate JWT and get userId
        Post updatedPost = postService.updatePost(postId, userId, postDetails);
        String notificationMessage = "Post updated: " + postDetails.getTitle();
        messageProducer.sendMessage("notificationExchange", "notificationQueue", notificationMessage);
        return ResponseEntity.ok(updatedPost);
    }

    // Delete a user's post
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@RequestHeader("Authorization") String authorization, @PathVariable Long postId) {
        String token = authorization.substring(7); // Extract token
        Long userId = postService.validateAndGetUserIdFromToken(token); // Validate JWT and get userId
        postService.deletePost(postId, userId);
        String notificationMessage = "Post deleted: " + postId;
        messageProducer.sendMessage("notificationExchange", "notificationQueue", notificationMessage);
        return ResponseEntity.noContent().build();
    }

    // Get all posts for a user
    @GetMapping
    public ResponseEntity<List<Post>> getAllPostsForUser(@RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7); // Extract token
        Long userId = postService.validateAndGetUserIdFromToken(token); // Validate JWT and get userId
        List<Post> posts = postService.getAllPostsForUser(userId);
        return ResponseEntity.ok(posts);
    }
}
