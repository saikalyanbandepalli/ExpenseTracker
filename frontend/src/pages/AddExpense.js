import React from 'react'; 
import ExpenseForm from '../components/ExpenseForm';
import InnerDashboard from './InnerDashboard';

const AddExpense = () => (
  <div>
    <h1>Add New Expense</h1>
    <InnerDashboard/>
    <ExpenseForm />
  </div>
);

export default AddExpense;
