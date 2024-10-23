import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import axiosInstance from '../hooks/axiosInstance';
import './Login.css'


const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const { login } = useAuth(); // Get login function from context

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await axiosInstance.post('api/users/login', { username, password });
            if (response.status === 200) {
                const { token } = response.data; // Assuming your API response contains the JWT token
                localStorage.setItem('token', token); 
                setUsername(username)// St
                login(username); // Set authenticated state
                navigate('/add-expense');
                console.log(username); // Redirect to home after login
            }

        } catch (err) {
            setError('Invalid username or password');
        }
    };

    return (
        <div className="login-container">
            <h1>Login</h1>
            <form onSubmit={handleLogin}>
                <div>
                    <label htmlFor="username">Username:</label>
                    <input
                        type="text"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Login</button>
            </form>
            {error && <p className="error">{error}</p>}
        </div>
    );
};

export default Login;
