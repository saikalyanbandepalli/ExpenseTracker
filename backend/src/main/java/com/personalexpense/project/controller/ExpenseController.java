package com.personalexpense.project.controller;


import com.personalexpense.project.dto.ExpenseRequest;
import com.personalexpense.project.exception.ResourceNotFoundException;
import com.personalexpense.project.model.Expense;
import com.personalexpense.project.model.User;
import com.personalexpense.project.services.ExpenseService;
import com.personalexpense.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<Expense> addExpense(@RequestBody ExpenseRequest expenseRequest) throws ResourceNotFoundException {
        Optional<User> user = userService.findById(expenseRequest.getUserId());


        Expense expense = new Expense(
                expenseRequest.getId(),
                expenseRequest.getName(),
                expenseRequest.getAmount(),
                expenseRequest.getCategory(),
                expenseRequest.getDate(),
                user
        );
        return ResponseEntity.ok(expenseService.addExpense(expense));
    }

    @GetMapping
    public List<Expense> getExpenses(String email) throws ResourceNotFoundException {

        return expenseService.getExpensesByUser(email);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }
}
