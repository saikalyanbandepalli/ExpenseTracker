package com.personalexpense.project.controller;


import com.personalexpense.project.model.Expense;
import com.personalexpense.project.model.User;
import com.personalexpense.project.services.ExpenseService;
import com.personalexpense.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public Expense addExpense(@RequestBody Expense expense, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        expense.setUser(user);
        return expenseService.addExpense(expense);
    }

    @GetMapping
    public List<Expense> getExpenses(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return expenseService.getExpensesByUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }
}
