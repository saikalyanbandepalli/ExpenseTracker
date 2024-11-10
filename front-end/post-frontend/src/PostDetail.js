import React, { useState, useEffect } from 'react';
import { getPostById, updatePost } from '../services/PostService';

const PostDetail = ({ token, postId }) => {
  const [post, setPost] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  useEffect(() => {
    if (postId) {
      getPostById(token, postId)
        .then((response) => {
          setPost(response.data);
          setTitle(response.data.title);
          setContent(response.data.content);
        })
        .catch((error) => console.error('Error fetching post:', error));
    }
  }, [token, postId]);

  const handleUpdate = (e) => {
    e.preventDefault();
    const updatedPost = { title, content };
    updatePost(token, postId, updatedPost)
      .then((response) => {
        setPost(response.data);
        setIsEditing(false);
      })
      .catch((error) => console.error('Error updating post:', error));
  };

  if (!post) return <p>Loading...</p>;

  return (
    <div>
      <h3>{isEditing ? 'Edit Post' : post.title}</h3>
      {isEditing ? (
        <form onSubmit={handleUpdate}>
          <label>
            Title:
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </label>
          <br />
          <label>
            Content:
            <textarea
              value={content}
              onChange={(e) => setContent(e.target.value)}
            />
          </label>
          <br />
          <button type="submit">Update Post</button>
        </form>
      ) : (
        <div>
          <p>{post.content}</p>
          <button onClick={() => setIsEditing(true)}>Edit</button>
        </div>
      )}
    </div>
  );
};

export default PostDetail;
