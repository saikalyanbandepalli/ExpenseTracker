import React, { useContext, useEffect } from 'react';
import { useDispatch } from 'react-redux'; // Import Redux hook
import ExpenseItem from './ExpenseItem';
import './ExpenseForm.css';
import ExpenseContext from '../context/ExpenseContext';
import { setExpenses } from '../components/expenseSlice'; // Redux action
import axios from 'axios';
import axiosInstance from '../hooks/axiosInstance';

const ExpenseList = () => {
  const dispatch = useDispatch(); // Create dispatch function for Redux
  const { expenses, setExpenses: setContextExpenses } = useContext(ExpenseContext); // Use existing ExpenseContext

  useEffect(() => {
    const fetchExpenses = async () => {
      try {
        const response = await axiosInstance.get('/api/expenses/allexpenses', {
          params: { loggedUser: 'yoshirin' }, // Example: passing logged user as a parameter
        });

        // Update context and Redux store
        setContextExpenses(response.data); // Store expenses in Context
        dispatch(setExpenses(response.data)); // Also store expenses in Redux for persistence
      } catch (error) {
        console.error('Error fetching expenses:', error);
      }
    };

    fetchExpenses();
  }, [dispatch, setContextExpenses]); // Keep Context logic intact, add dispatch for Redux

  return (
    <ul>
      {expenses.map((expense) => (
        <ExpenseItem key={expense.id} expense={expense} />
      ))}
    </ul>
  );
};

export default ExpenseList;
