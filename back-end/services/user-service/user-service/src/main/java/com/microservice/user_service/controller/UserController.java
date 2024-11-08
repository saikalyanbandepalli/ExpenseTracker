package com.microservice.user_service.controller;



import com.microservice.user_service.DTO.LoginDTO;
import com.microservice.user_service.model.Role;
import com.microservice.user_service.model.User;
import com.microservice.user_service.repostitory.RoleRepository;
import com.microservice.user_service.repostitory.UserRepository;
import com.microservice.user_service.service.UserService;
import com.netflix.appinfo.InstanceInfo;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO) {
        System.out.println("this is from login  " + loginDTO.getUsername());
        System.out.println(roleRepository.findRolesByUsername(loginDTO.getUsername()));
        String jwtToken = userService.authenticateUser(loginDTO);

        System.out.println(jwtToken);
        if (jwtToken != null) {
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
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

