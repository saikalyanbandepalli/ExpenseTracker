import React from 'react'; 
import { useContext } from 'react';
import ExpenseItem from './ExpenseItem';
import ExpenseContext from '../context/ExpenseContext';

const ExpenseList = () => {
  const { expenses } = useContext(ExpenseContext);

  return (
    <ul>
      {expenses.map(expense => (
        <ExpenseItem key={expense.id} expense={expense} />
      ))}
    </ul>
  );
};

export default ExpenseList;
