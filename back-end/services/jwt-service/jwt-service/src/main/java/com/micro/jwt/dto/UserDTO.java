package com.micro.jwt.dto;



import java.util.List;

public class UserDTO {
    private String username;
    private String password;
    private List<RoleDTO> roles; // RoleDTO can be another simple DTO representing roles

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

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }
}

