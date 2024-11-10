import React, { useState } from "react";
import { addComment, reactToComment } from "./CommentService";

const CommentSection = ({ postId, userId }) => {
  const [commentText, setCommentText] = useState("");
  const [comments, setComments] = useState([]); // Assuming an array of comments will be returned by your service
  const [reaction, setReaction] = useState("");

  const handleCommentSubmit = async () => {
    if (commentText.trim()) {
      try {
        await addComment(userId, { text: commentText });
        setCommentText("");
        // You can refresh the comments here or update the state directly
        alert("Comment added successfully!");
      } catch (error) {
        alert("Error adding comment");
      }
    }
  };

  const handleReactionSubmit = async (commentId) => {
    if (reaction.trim()) {
      try {
        await reactToComment(postId, userId, { commentId, reaction });
        alert("Reaction added successfully!");
      } catch (error) {
        alert("Error adding reaction");
      }
    }
  };

  return (
    <div>
      <h3>Comment Section</h3>

      <div>
        <textarea
          placeholder="Write your comment..."
          value={commentText}
          onChange={(e) => setCommentText(e.target.value)}
        ></textarea>
        <button onClick={handleCommentSubmit}>Add Comment</button>
      </div>

      <div>
        <h4>Comments</h4>
        {comments.length > 0 ? (
          comments.map((comment) => (
            <div key={comment.id} style={styles.commentContainer}>
              <p>{comment.text}</p>
              <p>By: {comment.user}</p>

              <input
                type="text"
                placeholder="React to this comment"
                value={reaction}
                onChange={(e) => setReaction(e.target.value)}
              />
              <button onClick={() => handleReactionSubmit(comment.id)}>
                React
              </button>
            </div>
          ))
        ) : (
          <p>No comments yet</p>
        )}
      </div>
    </div>
  );
};

const styles = {
  commentContainer: {
    border: "1px solid #ddd",
    borderRadius: "5px",
    padding: "10px",
    marginBottom: "10px",
    backgroundColor: "#f9f9f9",
  },
};

export default CommentSection;
