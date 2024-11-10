import React, { useState } from 'react';
import axios from 'axios';

const DeleteUser = ({ userId }) => {
  const [message, setMessage] = useState('');

  const handleDelete = async () => {
    try {
      await axios.delete(`/api/users/id/${userId}`);
      setMessage('User deleted successfully!');
    } catch (error) {
      setMessage('Error deleting user.');
    }
  };

  return (
    <div>
      <button onClick={handleDelete}>Delete User</button>
      <p>{message}</p>
    </div>
  );
};

export default DeleteUser;
