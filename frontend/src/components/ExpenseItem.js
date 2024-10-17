import React from 'react'; 
import './ExpenseItem.css'
const ExpenseItem = ({ expense }) => (
    <li>{expense.name}: ${expense.amount}</li>
  );
  
  export default ExpenseItem;
  