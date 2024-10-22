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
        //System.out.println("the user id is " + expenseRequest.getUserId());
        System.out.println("the loggged in user is " + expenseRequest.getLoggedUser());
         User user = userService.findByUsername(expenseRequest.getLoggedUser());
        if (user == null) {
            throw new ResourceNotFoundException("User not found with username: " + expenseRequest.getLoggedUser());
        }
            //User loggedInUser = user.get();
        System.out.println("the user is "+user);
//        if (!user.isPresent()) {
//            throw new ResourceNotFoundException("User not found with id: " + expenseRequest.getUserId());
//        }
    //System.out.println(user.get());
        Expense expense = new Expense(
                //expenseRequest.getId(),
                expenseRequest.getName(),
                expenseRequest.getAmount(),
                expenseRequest.getCategory(),
                expenseRequest.getDate(),
                //expenseRequest.getLoggedUser(),
                user
        );
        return ResponseEntity.ok(expenseService.addExpense(expense));
    }

    @GetMapping
    public List<Expense> getExpensesByemail(String email) throws ResourceNotFoundException {

        return expenseService.getExpensesByUser(email);
    }

    @GetMapping("/allexpenses")
    public List<Expense> getExpensesByUsername(@RequestParam("loggedUser")  String username) throws ResourceNotFoundException {
        System.out.println("the username is "+username);
        //userService.getExpensesByUsername(username);
        return expenseService.getExpensesByUser(username);
    }

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses() throws ResourceNotFoundException {
        return expenseService.getAllExpenses();
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }
}
