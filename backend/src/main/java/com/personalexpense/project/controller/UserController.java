package com.personalexpense.project.controller;


import com.personalexpense.project.Jwt.JwtUtil;
import com.personalexpense.project.config.SecurityConfig;
import com.personalexpense.project.dto.LoginRequest;
import com.personalexpense.project.dto.LoginResponse;
import com.personalexpense.project.model.Expense;
import com.personalexpense.project.model.Role;
import com.personalexpense.project.model.User;
import com.personalexpense.project.repositories.RoleRepository;
import com.personalexpense.project.services.ExpenseService;
import com.personalexpense.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    User user;

    @Autowired
    SecurityConfig securityConfig;

    @Autowired
    PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        System.out.println("Creating User: " + user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        Role defaultRole = roleRepository.findByName("ROLE_USER");
        if (defaultRole != null) {
            user.setRoles(Set.of(defaultRole));
        }
        // Register the user first
        User createdUser = userService.registerUser(user);

        // Ensure the created user is not null before proceeding
        if (createdUser != null) {
            // Check if the user has expenses and associate them with the created user
            if (user.getExpenses() != null && !user.getExpenses().isEmpty()) {
                for (Expense expenseRequest : user.getExpenses()) {
                    // Create a new Expense entity from the DTO
                    Expense expense = new Expense();
                    expense.setName(expenseRequest.getName());
                    expense.setAmount(expenseRequest.getAmount());
                    expense.setCategory(expenseRequest.getCategory());
                    expense.setDate(expenseRequest.getDate());

                    // Associate with the created user
                    expense.setUser(createdUser);

                    // Log the expense before saving
                    System.out.println("Expense to be saved: " + expense + " with user: " + createdUser.getUsername());

                    // Save the expense
                    expenseService.addExpense(expense);
                }
            } else {
                System.out.println("No expenses to save for user: " + createdUser.getUsername());
            }

            System.out.println("Created User: " + createdUser);


            return ResponseEntity.ok(createdUser);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Handle user creation failure
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println(loginRequest.getUsername());
            if (loginRequest.getUsername() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("User not found"));
            }
            // Authenticate the user using the provided username and password
            Authentication auth = authenticationManager.authenticate(

                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());

            // Generate JWT
            String jwt = jwtUtil.generateToken(userDetails);

            // Set the authentication in the context
            SecurityContextHolder.getContext().setAuthentication(auth);
            //System.out.println(auth);

            // Load user details to generate the JWT

            System.out.println("Loading" + jwt);
            // Return the JWT in the response
            return ResponseEntity.ok(new LoginResponse(jwt));

        } catch (BadCredentialsException e) {
            // Return error response with a message
            String errorMessage = "Invalid credentials";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(errorMessage));
        } catch (Exception e) {
            // Handle other exceptions (e.g., user not found)
            e.printStackTrace();
            String errorMessage = "An error occurred during authentication";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginResponse(errorMessage));
        }
    }




}







