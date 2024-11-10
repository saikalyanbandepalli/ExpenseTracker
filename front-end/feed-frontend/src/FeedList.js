import React, { useState, useEffect } from "react";
import { getUserFeed } from "./FeedService";

const FeedList = ({ userId }) => {
  const [posts, setPosts] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchFeed();
  }, []);

  const fetchFeed = async () => {
    try {
      const postsData = await getUserFeed(userId);
      setPosts(postsData);
    } catch (error) {
      setError("Error fetching user feed.");
    }
  };

  return (
    <div>
      <h2>User Feed</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      {posts.length > 0 ? (
        <div>
          {posts.map((post) => (
            <div key={post.id} style={styles.postContainer}>
              <h3>{post.title}</h3>
              <p>{post.content}</p>
              <p><strong>By:</strong> {post.author}</p>
              <p><strong>Date:</strong> {new Date(post.createdAt).toLocaleString()}</p>
            </div>
          ))}
        </div>
      ) : (
        <p>No posts found</p>
      )}
    </div>
  );
};

const styles = {
  postContainer: {
    border: "1px solid #ddd",
    borderRadius: "5px",
    padding: "10px",
    marginBottom: "10px",
    backgroundColor: "#f9f9f9",
  },
};

export default FeedList;
