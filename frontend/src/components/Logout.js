import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './Logout.css';

const Logout = () => {
    const navigate = useNavigate();
    const { logout } = useAuth();

    useEffect(() => {
        console.log('Logging out...');
        logout();
        localStorage.removeItem('token'); // Remove token
        navigate('/Login'); // Redirect to login page
    }, [logout, navigate]);

    return (
        <div className="logout-container">
            <h1>Logging out...</h1>
        </div>
    );
};

export default Logout;
