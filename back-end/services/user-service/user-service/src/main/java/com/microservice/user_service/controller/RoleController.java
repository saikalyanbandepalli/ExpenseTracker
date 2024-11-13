package com.microservice.user_service.controller;

import com.microservice.user_service.DTO.LoginDTO;
import com.microservice.user_service.model.Role;
import com.microservice.user_service.repostitory.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/getroles")
    public List<Role> returnroles() {

    return roleRepository.findAll();
    }
}
