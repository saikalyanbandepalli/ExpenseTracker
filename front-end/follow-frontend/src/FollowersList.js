import React, { useEffect, useState } from "react";
import { getFollowers } from "./FollowService";

const FollowersList = ({ userId }) => {
  const [followers, setFollowers] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchFollowers();
  }, []);

  const fetchFollowers = async () => {
    try {
      const followersData = await getFollowers(userId);
      setFollowers(followersData);
    } catch (error) {
      setError("Error fetching followers.");
    }
  };

  return (
    <div>
      <h2>Followers</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      {followers.length > 0 ? (
        <ul>
          {followers.map((follower) => (
            <li key={follower.id}>
              {follower.name} - {follower.email}
            </li>
          ))}
        </ul>
      ) : (
        <p>No followers found</p>
      )}
    </div>
  );
};

export default FollowersList;
