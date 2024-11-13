package com.microservice.user_service.service;


import com.microservice.user_service.DTO.LoginDTO;
import com.microservice.user_service.model.Role;
import com.microservice.user_service.model.User;

import com.microservice.user_service.repostitory.RoleRepository;
import com.microservice.user_service.repostitory.UserRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    //private PasswordEncoder     passwordEncoder;


    // UserService code
    public ResponseEntity<String> authenticateUserAndGetToken(LoginDTO loginDTO) {
        User existingUser = userRepository.findByUsername(loginDTO.getUsername());
        if (existingUser == null || !existingUser.getPassword().equals(loginDTO.getPassword())) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        // If credentials are valid, make a call to JWT service to generate a token
        String jwtServiceUrl = "http://localhost:8089/api/auth/generate-token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request with user ID and username
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", existingUser.getId());
        payload.put("username", existingUser.getUsername());

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(jwtServiceUrl, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println("Error calling JWT service: " + e.getMessage());
        }

        return new ResponseEntity<>("Failed to generate token", HttpStatus.INTERNAL_SERVER_ERROR);
    }





    public User registerUser(User user) {
        // Fetch roles from database by name
        List<Role> existingRoles = user.getRoles().stream()
                .map(role -> roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + role.getName())))
                .collect(Collectors.toList());

        // Assign existing roles to the user
        user.setRoles(existingRoles);

        // Save the user
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);

        // Check if the user exists
        if (optionalUser.isPresent()) {
            User user = optionalUser.get(); // Retrieve the actual user object

            // Update user fields
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());

            // Optionally update the password if needed (ensure to handle password hashing)
            if (userDetails.getPassword() != null) {
                user.setPassword(userDetails.getPassword()); // Ensure proper password hashing before saving
            }

            // Save the updated user to the repository
            return userRepository.save(user);
        }

        // Return null if the user was not found
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserByUsername(String username) {
       return userRepository.findByUsername(username);
    }


    public Long getIdByUsername(String username) {
        return userRepository.getIdByUsername(username);
    }
}

