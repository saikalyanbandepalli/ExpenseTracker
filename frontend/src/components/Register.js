import React, { useEffect, useState } from 'react';
import axiosInstance from '../hooks/axiosInstance';

const Register = () => {
    const [roles, setRoles] = useState([]); // Initialize roles as an empty array
    const [selectedRoles, setSelectedRoles] = useState([]); // State to track selected roles
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState('');

    useEffect(() => {
        const fetchRoles = async () => {
            try {
                const response = await axiosInstance.get('/api/roles/getroles'); // Adjust the endpoint as needed
               // console.log(response.data);
               console.log("Roles before sending:", selectedRoles.map(role => ({ name: role })));
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
    
            console.log("Updated Selected Roles:", newSelected); // Ensure this shows the correct role selection
            return newSelected;
        });
    };
    const handleRegister = async (e) => {
        e.preventDefault();

        const payload = {
            username,
            password,
            email,
            roles: selectedRoles.map(role => ({ name: role })), // Convert selected roles to an array of objects
        };

        console.log("Payload being sent:", JSON.stringify(payload, null, 2)); // Log the payload

        try {
            const response = await axiosInstance.post('/api/users/register', payload);
            setMessage('Registration successful!');
        } catch (error) {
            console.error('Registration error:', error.response ? error.response.data : error);
            setMessage('Registration failed. Please try again.');
        }
    };

    return (
        <div>
            <h2>Register</h2>
            <form onSubmit={handleRegister}>
                <div>
                    <label>Username:</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Email:</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Roles:</label>
                    {Array.isArray(roles) && roles.length > 0 ? (
                        roles.map((role) => (
                            <div key={role.id}>
                                <input
                                    type="checkbox"
                                    id={role.name}
                                    value={role.name}
                                    checked={selectedRoles.includes(role.name)} // Check if this role is selected
                                    onChange={() => handleRoleChange(role.name)} // Handle role selection
                                />
                                <label htmlFor={role.name}>{role.name}</label>
                            </div>
                        ))
                    ) : (
                        <p>No roles available.</p>
                    )}
                </div>
                <button type="submit">Register</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default Register;
