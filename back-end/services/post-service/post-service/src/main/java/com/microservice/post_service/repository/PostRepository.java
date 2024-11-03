package com.microservice.post_service.repository;

//package com.example.postservice.repository;

import com.microservice.post_service.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}

