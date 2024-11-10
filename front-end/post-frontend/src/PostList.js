import React, { useEffect, useState } from 'react';
import { getAllPosts } from '../services/PostService';

const PostList = ({ token }) => {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    if (token) {
      getAllPosts(token)
        .then((response) => setPosts(response.data))
        .catch((error) => console.error('Error fetching posts:', error));
    }
  }, [token]);

  return (
    <div>
      <h2>All Posts</h2>
      {posts.length === 0 ? (
        <p>No posts available.</p>
      ) : (
        <ul>
          {posts.map((post) => (
            <li key={post.id}>
              <h3>{post.title}</h3>
              <p>{post.content}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default PostList;
