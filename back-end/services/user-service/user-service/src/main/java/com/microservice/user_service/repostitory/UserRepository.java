package com.microservice.user_service.repostitory;

//package com.example.userservice.repository;

import com.microservice.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     User findByUsername(String username);
     Optional<User> findById(Long id);
}

