package com.personalexpense.project.controller;


import com.personalexpense.project.dto.ExpenseRequest;
import com.personalexpense.project.model.Expense;
import com.personalexpense.project.model.User;
import com.personalexpense.project.services.ExpenseService;
import com.personalexpense.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ExpenseService expenseService;


    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        System.out.println("Creating User: " + user);

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




}
