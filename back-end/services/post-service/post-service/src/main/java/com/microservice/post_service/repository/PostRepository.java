package com.microservice.post_service.repository;

//package com.example.postservice.repository;

import com.microservice.post_service.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
    Optional<Post> findByIdAndUserId(Long id, Long userId);
    List<Post> findAllByUserId(Long userId);
}

