import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from './AuthContext';
import './Logout.css'

const Logout = () => {
    const navigate = useNavigate();
    const { logout } = useAuth();

    React.useEffect(() => {
        logout(); // Clear authentication state
        localStorage.removeItem('token'); 
        navigate('/login'); // Redirect to login
    }, [logout, navigate]);

    return (
        <div className="logout-container">
            <h1>Logging out...</h1>
        </div>
    );
};

export default Logout;
