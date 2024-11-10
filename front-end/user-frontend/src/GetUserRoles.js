import React, { useState } from 'react';
import axios from 'axios';

const GetUserRoles = () => {
  const [username, setUsername] = useState('');
  const [roles, setRoles] = useState([]);
  const [error, setError] = useState('');

  const fetchRoles = async () => {
    try {
      const response = await axios.get(`/api/users/${username}/roles`);
      setRoles(response.data);
      setError('');
    } catch (error) {
      setError('Error fetching user roles.');
    }
  };

  return (
    <div>
      <h2>Get User Roles</h2>
      <div>
        <label>Username: </label>
        <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} required />
        <button onClick={fetchRoles}>Get Roles</button>
      </div>
      {roles.length > 0 && (
        <div>
          <h3>Roles</h3>
          <ul>
            {roles.map((role, index) => (
              <li key={index}>{role}</li>
            ))}
          </ul>
        </div>
      )}
      {error && <p>{error}</p>}
    </div>
  );
};

export default GetUserRoles;
