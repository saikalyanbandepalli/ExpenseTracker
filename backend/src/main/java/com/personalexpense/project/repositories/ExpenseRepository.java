package com.personalexpense.project.repositories;




import com.personalexpense.project.model.Expense;
import com.personalexpense.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT e FROM Expense e JOIN e.user u WHERE u.email = :email")
    List<Expense> findByUserEmail(String email);

    List<Expense> findAll();
    //List<Expense> getAllExpenses();


}

