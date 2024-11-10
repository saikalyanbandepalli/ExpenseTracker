import React, { useState } from 'react';
import { createPost } from '../services/PostService';

const CreatePost = ({ token, onPostCreated }) => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    const newPost = { title, content };
    createPost(token, newPost)
      .then((response) => {
        onPostCreated(response.data); // Inform parent component
        setTitle('');
        setContent('');
      })
      .catch((error) => console.error('Error creating post:', error));
  };

  return (
    <form onSubmit={handleSubmit}>
      <h3>Create New Post</h3>
      <label>
        Title:
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
      </label>
      <br />
      <label>
        Content:
        <textarea
          value={content}
          onChange={(e) => setContent(e.target.value)}
          required
        />
      </label>
      <br />
      <button type="submit">Create Post</button>
    </form>
  );
};

export default CreatePost;
