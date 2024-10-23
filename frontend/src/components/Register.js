import React, { useEffect, useState } from 'react';
import axiosInstance from '../hooks/axiosInstance';
import { useNavigate } from 'react-router-dom';

const Register = () => {
    const [roles, setRoles] = useState([]);
    const [selectedRoles, setSelectedRoles] = useState([]);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState(''); // To handle error messages
    const navigate = useNavigate();

    useEffect(() => {
        const fetchRoles = async () => {
            try {
                const response = await axiosInstance.get('/api/roles/getroles'); // Adjust the endpoint as needed
                setRoles(response.data);
            } catch (error) {
                console.error('Error fetching roles:', error);
            }
        };

        fetchRoles();
    }, []);

    const handleRoleChange = (role) => {
        setSelectedRoles((prevSelected) => {
            const newSelected = prevSelected.includes(role)
                ? prevSelected.filter((r) => r !== role)
                : [...prevSelected, role];

            return newSelected;
        });
    };

    const handleRegister = async (e) => {
        e.preventDefault();

        // Check if any required fields are missing
        if (!username) {
            setErrorMessage('Please enter a username.');
            return;
        }
        if (!password) {
            setErrorMessage('Please enter a password.');
            return;
        }
        if (!email) {
            setErrorMessage('Please enter an email.');
            return;
        }
        if (selectedRoles.length === 0) {
            setErrorMessage('Please select at least one role.');
            return;
        }

        const payload = {
            username,
            password,
            email,
            roles: selectedRoles.map(role => ({ name: role })),
        };

        try {
            const response = await axiosInstance.post('/api/users/register', payload);
            setMessage('Registration successful!');
            navigate('/login');
        } catch (error) {
            console.error('Registration error:', error.response ? error.response.data : error);
            setMessage('Registration failed. Please try again.');
        }
    };

    return (
        <div className="container mt-5">
            <h2 className="text-center">Register</h2>
            <form onSubmit={handleRegister} className="bg-light p-4 rounded shadow">
                <div className="mb-3">
                    <label className="form-label">Username:</label>
                    <input
                        type="text"
                        className="form-control"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label className="form-label">Password:</label>
                    <input
                        type="password"
                        className="form-control"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label className="form-label">Email:</label>
                    <input
                        type="email"
                        className="form-control"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label className="form-label">Roles:</label>
                    {Array.isArray(roles) && roles.length > 0 ? (
                        roles.map((role) => (
                            <div key={role.id}>
                                <input
                                    type="checkbox"
                                    id={role.name}
                                    value={role.name}
                                    checked={selectedRoles.includes(role.name)}
                                    onChange={() => handleRoleChange(role.name)}
                                />
                                <label htmlFor={role.name} className="ms-2">{role.name}</label>
                            </div>
                        ))
                    ) : (
                        <p>No roles available.</p>
                    )}
                </div>
                <button type="submit" className="btn btn-primary w-100">Register</button>
            </form>
            {errorMessage && <p className="text-danger text-center mt-3">{errorMessage}</p>}
            {message && <p className="text-success text-center mt-3">{message}</p>}
        </div>
    );
};

export default Register;
