package com.micro.jwt.controller;


import com.micro.jwt.Jwt.JwtUtil;
import com.micro.jwt.dto.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    private final String USER_SERVICE_URL = "http://user-service/api/users"; // Adjust according to actual URL

    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) throws Exception {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();

//        // Verify credentials via UserService
//        Boolean isAuthenticated = restTemplate.postForObject(
//                USER_SERVICE_URL + "/authenticate",
//                new UsernamePasswordAuthenticationToken(username, password),
//                Boolean.class
//        );
//
//        if (!Boolean.TRUE.equals(isAuthenticated)) {
//            throw new Exception("Invalid username or password");
//        }

        // Generate and return the JWT if authentication succeeds
        return jwtUtil.generateToken(username);
    }

    @GetMapping("/validate")
    public boolean validateToken(@RequestParam String token) {
        String username = jwtUtil.extractUsername(token);
        return username != null && jwtUtil.validateToken(token, username);
    }
}
