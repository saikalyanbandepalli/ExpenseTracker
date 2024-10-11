import React from 'react';
import { ExpenseProvider } from './context/ExpenseContext'; // Adjust the path as necessary
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar'; // Adjust the path as necessary
import Dashboard from './pages/Dashboard'; // Adjust the path as necessary
import AddExpense from './pages/AddExpense'; // Adjust the path as necessary
import GetUser from './components/GetUser'; // New component to get user details
import Register from './components/Register'; // New component for user registration

function App() {
    return (
        <ExpenseProvider>
            <Router>
                <Navbar />
                <Routes>
                    <Route path="/" element={<Dashboard />} />
                    <Route path="/add-expense" element={<AddExpense />} />
                    <Route path="/get-user" element={<GetUser />} /> {/* New route */}
                    <Route path="/register" element={<Register />} /> {/* New route */}
                    {/* Add other routes as needed */}
                </Routes>
            </Router>
        </ExpenseProvider>
    );
}

export default App;
