import React, { useState } from 'react';
import axios from 'axios';

const LoginUser = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Send a POST request to the login endpoint with username and password
      const response = await axios.post('http://localhost:8081/api/users/login', { username, password }, {
        withCredentials: true,  // Ensures cookies (authToken) are sent and received
      });

      // Handle successful login
      console.log('Login successful', response.data);

      // Clear any previous error messages
      setError('');
    } catch (error) {
      // Handle error cases, such as incorrect login credentials
      setError('Invalid username or password');
      console.error('Login failed:', error.response ? error.response.data : error.message);
    }
  };

  return (
    <div>
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Username: </label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Password: </label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Login</button>
      </form>
      {error && <p>{error}</p>}
    </div>
  );
};

export default LoginUser;
