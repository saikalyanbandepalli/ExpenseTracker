package com.personalexpense.project.services;



import com.personalexpense.project.model.User;
import com.personalexpense.project.repositories.ExpenseRepository;
import com.personalexpense.project.repositories.RoleRepository;
import com.personalexpense.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExpenseRepository       expenseRepository;

    private final User user;
    // Assuming User is your user entity
    private final List<GrantedAuthority> authorities;



    @Autowired
    private RoleRepository roleRepository;

    public UserService(User user) {
        this.user = user;
        this.authorities = user.getRoles() != null ?
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()) :
                Collections.emptyList(); // Handle empty roles
    }


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public User registerUser(User user) {
        // Perform validation and encryption (if necessary)
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User authenticateUser(String username, String password) {

        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        if(user.getUsername() == username && user.getPassword() == password) {
            return user;
        }


        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Assign a default role if there are no roles assigned
        List<GrantedAuthority> authorities = user.getRoles().isEmpty() ?
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) :
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);
    }
}

