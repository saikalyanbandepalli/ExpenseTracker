import React, { useState } from "react";
import FeedList from "./FeedList";

const App = () => {
  const [userId, setUserId] = useState(1); // Default userId, you can change it dynamically

  return (
    <div>
      <h1>User Feed Example</h1>

      {/* Input to change the userId dynamically */}
      <div>
        <label htmlFor="userId">Enter User ID:</label>
        <input
          type="number"
          id="userId"
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
        />
      </div>

      {/* Display the user's feed */}
      <FeedList userId={userId} />
    </div>
  );
};

export default App;
