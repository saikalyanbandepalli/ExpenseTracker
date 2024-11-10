package com.microservice.follow_service.dto;



import java.util.List;

public class UserDTO {

    //private Long id;
    private String username;
    private String email;


    // Constructors
    public UserDTO() {
    }

    public UserDTO(String username, String email, List<String> roles) {
        //this.id = id;
        this.username = username;
        this.email = email;
        //this.roles = roles;
    }

    // Getters and Setters
//    public Long getId() {
//        return id;
//    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public List<String> getRoles() {
//        return roles;
//    }

//    public void setRoles(List<String> roles) {
//        this.roles = roles;
//    }

    @Override
    public String toString() {
        return "UserDTO{" +
                //"id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
               // ", roles=" + roles +
                '}';
    }
}
