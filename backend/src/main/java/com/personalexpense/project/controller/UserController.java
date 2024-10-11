package com.personalexpense.project.controller;


import com.personalexpense.project.model.Expense;
import com.personalexpense.project.model.User;
import com.personalexpense.project.services.ExpenseService;
import com.personalexpense.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User registerUser(@RequestBody User user) {
        System.out.println("Creating User: " + user);
        User createdUser = userService.registerUser(user);
        for (Expense expense : user.getExpenses()) {
            expenseService.addExpense(expense);
            expense.setUser(user);
            // Associate expense with the user
            System.out.println("Expense to be saved: " + expense);
        }

        // Save the user
        System.out.println("Created User: " + createdUser);

        return ResponseEntity.ok(createdUser).getBody(); // Return the created user
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        return userService.findByUsername(username);
    }
}
