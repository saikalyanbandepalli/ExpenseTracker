import React, { useContext, useState } from 'react';
import axios from 'axios';
import ExpenseContext from '../context/ExpenseContext';

const ExpenseForm = () => {
  const { setExpenses } = useContext(ExpenseContext);
  const [name, setName] = useState('');
  const [amount, setAmount] = useState(0);
  const [error, setError] = useState(null);
  //const userId = 1; // Replace with the actual user ID

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('/api/expenses/add', {
        name,
        amount,
        //userId, // Include userId in the request
      });
      // Update local state with the newly added expense
      setExpenses(prevExpenses => [...prevExpenses, response.data]);
      setName('');
      setAmount(0);
    } catch (err) {
      setError('Failed to add expense. Please try again.');
      console.error(err); // Log the error for debugging
    }
  };

  return (
    <div>
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
      {error && <p className="error">{error}</p>} {/* Display error message if any */}
    </div>
  );
};

export default ExpenseForm;
