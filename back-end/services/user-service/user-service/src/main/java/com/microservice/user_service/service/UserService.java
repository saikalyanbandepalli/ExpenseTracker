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

import java.util.List;
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
    @Autowired
    private EurekaClient eurekaClient;

    public String authenticateUser(LoginDTO loginDTO) {
        // Check if user exists
        User existingUser = userRepository.findByUsername(loginDTO.getUsername());

        if (existingUser == null) {
            System.out.println("User not found");
            return null;
        }

        // Use Eureka client to get JWT service URL
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("jwt-service", false);
        if (instanceInfo == null) {
            System.out.println("JWT service not found in Eureka");
            return null;
        }

        String jwtServiceUrl = instanceInfo.getHomePageUrl() + "api/auth/login";
        System.out.println("JWT Service URL: " + jwtServiceUrl);

        // Create LoginDTO request payload
        LoginDTO loginRequest = new LoginDTO(loginDTO.getUsername(), loginDTO.getPassword());

        try {
            RestTemplate restTemplate = new RestTemplate(); // Ideally, this should be a bean

            // Using postForObject to send request without HttpEntity
            String jwtToken = restTemplate.postForObject(jwtServiceUrl, loginRequest, String.class);

            if (jwtToken != null) {
                System.out.println("Received JWT token: " + jwtToken);
                return jwtToken;  // Return the JWT token
            } else {
                System.out.println("JWT service did not return a token.");
            }
        } catch (HttpClientErrorException | HttpServerErrorException | ResourceAccessException e) {
            System.out.println("Error during authentication with JWT service: " + e.getMessage());
            e.printStackTrace();
        }

        return null;  // Return null if authentication fails or there’s an issue
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


}

