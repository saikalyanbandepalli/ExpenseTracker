import React, { useState } from "react";
import FollowButton from "./FollowButton";
import FollowersList from "./FollowersList";

const App = () => {
  const [userId, setUserId] = useState(1); // Default userId, you can change this dynamically

  return (
    <div>
      <h1>Follow User Example</h1>

      {/* Example of following functionality */}
      <FollowButton followerId={userId} followeeId={2} /> {/* Follow user with ID 2 */}

      {/* List followers of a user */}
      <FollowersList userId={userId} />
    </div>
  );
};

export default App;
