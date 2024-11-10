package com.microservice.post_service.service;

import com.microservice.post_service.model.JwtTokenProvider;
import com.microservice.post_service.model.Post;
import com.microservice.post_service.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider; // Assumes you have a JwtTokenProvider for validating tokens

    // Method to validate JWT and retrieve userId
    public Long validateAndGetUserIdFromToken(String token) {
        if (jwtTokenProvider.validateToken(token)) {
            return jwtTokenProvider.getUserIdFromJWT(token);
        } else {
            throw new RuntimeException("Invalid JWT token");
        }
    }

    // Create a new post
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    // Get a post for a user
    public Post getPost(Long postId, Long userId) {
        return postRepository.findByIdAndUserId(postId, userId)
                .orElseThrow(() -> new RuntimeException("Post not found or you are not authorized to view it"));
    }

    // Update a post for a user
    public Post updatePost(Long postId, Long userId, Post postDetails) {
        Post post = getPost(postId, userId);
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        return postRepository.save(post);
    }

    // Delete a post for a user
    public void deletePost(Long postId, Long userId) {
        Post post = getPost(postId, userId);
        postRepository.delete(post);
    }

    // Get all posts for a user
    public List<Post> getAllPostsForUser(Long userId) {
        return postRepository.findAllByUserId(userId);
    }
}
