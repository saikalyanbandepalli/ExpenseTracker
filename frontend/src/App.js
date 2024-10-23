import React from 'react';
import { ExpenseProvider } from './context/ExpenseContext'; 
import { AuthProvider } from './context/AuthContext'; 
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar'; 
import Dashboard from './pages/Dashboard'; 
import AddExpense from './pages/AddExpense'; 
import GetUser from './components/GetUser'; 
import Register from './components/Register'; 
import Login from './components/Login';

import Logout from './components/Logout'; 

import NotFound from './components/NotFound'; // Import your custom NotFound component
import ServerError from './components/ServerError'; // Import your custom ServerError component
//import ErrorBoundary from './components/ErrorBoundary'; // Import ErrorBoundary
import { Provider } from 'react-redux'; // Redux Provider
import { PersistGate } from 'redux-persist/integration/react'; // PersistGate for Redux persist
import store, { persistor } from './components/store'; 

function App() {
    return (
        <Provider store={store}>
        <AuthProvider>
            <ExpenseProvider>
                <Router>
                    <Navbar />
                
                        <Routes>
                            <Route path="/" element={<Dashboard />} />
                            <Route path="/login" element={<Login />} />
                            <Route path="/logout" element={<Logout />} />
                            <Route path="/add-expense" element={<AddExpense />} />
                            <Route path="/get-user" element={<GetUser />} />
                            <Route path="/register" element={<Register />} />
                            <Route path="/getuser" element={<GetUser />} />
                            <Route path="/500" element={<ServerError />} /> {/* Custom Server Error Route */}
                            <Route path="*" element={<NotFound />} /> {/* Catch-all route for 404 */}
                        </Routes>
                
                </Router>
            </ExpenseProvider>
        </AuthProvider>
        </Provider>
        
    );
}

export default App;
