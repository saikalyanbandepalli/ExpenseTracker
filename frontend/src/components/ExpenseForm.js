import React from 'react'; 
import { useContext, useState } from 'react';
import ExpenseContext from '../context/ExpenseContext';

const ExpenseForm = () => {
  const { setExpenses } = useContext(ExpenseContext);
  const [name, setName] = useState('');
  const [amount, setAmount] = useState(0);

  const handleSubmit = (e) => {
    e.preventDefault();
    setExpenses(prevExpenses => [...prevExpenses, { name, amount, id: Date.now() }]);
    setName('');
    setAmount(0);
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="Expense Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
        required
      />
      <input
        type="number"
        placeholder="Amount"
        value={amount}
        onChange={(e) => setAmount(Number(e.target.value))}
        required
      />
      <button type="submit">Add Expense</button>
    </form>
  );
};

export default ExpenseForm;
