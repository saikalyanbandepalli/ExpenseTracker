import React from 'react'; 
const ExpenseItem = ({ expense }) => (
    <li>{expense.name}: ${expense.amount}</li>
  );
  
  export default ExpenseItem;
  