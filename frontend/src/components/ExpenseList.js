import React from 'react'; 
import { useContext } from 'react';
import ExpenseItem from './ExpenseItem';
import './ExpenseForm.css'
import ExpenseContext from '../context/ExpenseContext';

const ExpenseList = () => {
  const { expenses } = useContext(ExpenseContext);
  console.log("this is from expesnes list "+ expenses);
  return (
    
    <ul>
      {expenses.map(expense => (
        <ExpenseItem key={expense.id} expense={expense} />
      ))}
    </ul>
  );
};

export default ExpenseList;
