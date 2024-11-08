package com.microservice.user_service.DTO;

import com.microservice.user_service.model.Role;

import java.util.List;

public class LoginDTO {
    private String username;
    private String password;
   // private List<Role> roles;  // Add roles here, just like in UserDTO

    // Default constructor
    public LoginDTO() {}

    // Constructor with username, password, and roles
    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
        //this.roles = roles;
    }



    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public List<Role> getRoles() {
//        return roles;
//    }

//    public void setRoles(List<Role> roles) {
//        this.roles = roles;
//    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + (password != null ? "******" : null) + '\'' +
                '}';
    }
}
