import React, { useState } from "react";
import CommentSection from "./CommentSection";

const App = () => {
  const [userId] = useState(1); // Assume this is fetched from the logged-in user's data
  const [postId] = useState(123); // Example postId

  return (
    <div>
      <h1>Post and Comment Example</h1>

      {/* Displaying the comment section for a particular post */}
      <CommentSection postId={postId} userId={userId} />
    </div>
  );
};

export default App;
