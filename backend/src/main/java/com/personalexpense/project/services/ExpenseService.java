package com.personalexpense.project.services;


import com.personalexpense.project.model.Expense;
import com.personalexpense.project.model.User;
import com.personalexpense.project.repositories.ExpenseRepository;
import com.personalexpense.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;

    public Expense addExpense(Expense expense) {
        userRepository.save(expense.getUser());
        return expenseRepository.save(expense);
    }

    public List<Expense> getExpensesByUser(String gmail) {
        return expenseRepository.findByUserEmail(gmail);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}
