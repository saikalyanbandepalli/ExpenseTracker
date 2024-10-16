import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from './AuthContext';

const Logout = () => {
    const navigate = useNavigate();
    const { logout } = useAuth();

    React.useEffect(() => {
        logout(); // Clear authentication state
        navigate('/login'); // Redirect to login
    }, [logout, navigate]);

    return (
        <div className="logout-container">
            <h1>Logging out...</h1>
        </div>
    );
};

export default Logout;
