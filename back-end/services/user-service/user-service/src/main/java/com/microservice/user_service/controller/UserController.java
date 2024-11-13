package com.microservice.user_service.controller;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microservice.user_service.DTO.LoginDTO;
import com.microservice.user_service.model.Role;
import com.microservice.user_service.model.User;
import com.microservice.user_service.repostitory.RoleRepository;
import com.microservice.user_service.repostitory.UserRepository;
import com.microservice.user_service.service.UserService;
import com.netflix.appinfo.InstanceInfo;
import jakarta.servlet.http.Cookie;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository  roleRepository;
    @Autowired
    private UserRepository  userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.registerUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }






    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        // Authenticate user and get the JWT token
        ResponseEntity<String> jwtToken = userService.authenticateUserAndGetToken(loginDTO); // Directly get the token

        if (jwtToken != null) {
            // Create and set the cookie with the raw token (no need for Base64 encoding)
            Cookie cookie = new Cookie("authToken", jwtToken.getBody());  // Assuming jwtToken is the raw JWT string
            cookie.setHttpOnly(false);     // Prevent JavaScript access to the cookie for security
            cookie.setSecure(false);      // Allow cookies over HTTP for local development (set to true for production)
            cookie.setDomain("localhost");  // Make cookie available for all localhost subdomains and ports
            cookie.setPath("/");          // Cookie is accessible across the entire application
            cookie.setMaxAge(3600);       // Set the cookie expiration to 1 hour (in seconds)
            //cookie.setSameSite("None");    // Allow cross-origin requests, required for cookies across different ports

            // Log the cookie for debugging
            System.out.println("the cookies is "+ cookie.getName()+" and "+ cookie.getValue());

            // Add the cookie to the response
            response.addCookie(cookie);

            return jwtToken;  // Optionally, send raw token back in response body (if needed)
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }




    @GetMapping("/getuserid/{username}")
    public Long getUserId(@PathVariable String username) {
        return userService.getIdByUsername(username);

    }


    @GetMapping("/{username}/roles")
    public List<String> getUserRoles(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);

        // Check if the user exists
        if (user.isPresent()) {
            // Return the user object with HTTP status OK
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            // Return 404 Not Found if the user does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("id/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }
}

