package com.microservice.follow_service.DTO;



public class UserDTO {
    private Long id;
    private String username; // Add other fields as needed

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

