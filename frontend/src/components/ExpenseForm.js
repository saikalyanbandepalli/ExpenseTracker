import React, { useContext, useState } from 'react';
import axiosInstance from '../hooks/axiosInstance';
import ExpenseContext from '../context/ExpenseContext';
import { useAuth } from '../context/AuthContext'; // Import the AuthContext

const ExpenseForm = () => {
  const { setExpenses } = useContext(ExpenseContext);
  const {loggedUser} = useAuth(); // Get userId from AuthContex
  const [name, setName] = useState('');
  const [amount, setAmount] = useState(0);
  const [category, setCategory] = useState(''); // State for category
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    
    e.preventDefault();
    //console.log("The user id is " + userId);
    try {
      console.log("this is from expesnse form "+loggedUser);
      const response = await axiosInstance.post('/api/expenses/add', {
        name,
        amount,
        category, // Include category in the request
       // userId,
        loggedUser // Include userId in the request
      });
      console.log("this is from expese form "+loggedUser);
      
      // Update local state with the newly added expense
      setExpenses(prevExpenses => [...prevExpenses, response.data]);
      setName('');
      setAmount(0);
      setCategory(''); // Reset category after submission
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
        <input
          type="text"
          placeholder="Category"
          value={category}
          onChange={(e) => setCategory(e.target.value)} // Handle category input
          required
        />
        <button type="submit">Add Expense</button>
      </form>
      {error && <p className="error">{error}</p>} {/* Display error message if any */}
    </div>
  );
};

export default ExpenseForm;
