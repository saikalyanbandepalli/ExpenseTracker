import React, { useState } from "react";
import { followUser } from "./FollowService";

const FollowButton = ({ followerId, followeeId }) => {
  const [status, setStatus] = useState(null);

  const handleFollow = async () => {
    try {
      const response = await followUser(followerId, followeeId);
      setStatus(response); // Display success message
    } catch (error) {
      setStatus("Error following user.");
    }
  };

  return (
    <div>
      <button onClick={handleFollow}>Follow</button>
      {status && <p>{status}</p>}
    </div>
  );
};

export default FollowButton;
