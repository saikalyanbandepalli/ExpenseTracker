import React, { useState } from 'react';
import PostList from './components/PostList';
import CreatePost from './components/CreatePost';
import PostDetail from './components/PostDetail';

const App = () => {
  const [token, setToken] = useState(localStorage.getItem('jwtToken') || ''); // Save token in localStorage after login
  const [selectedPostId, setSelectedPostId] = useState(null);
  const [newPost, setNewPost] = useState(null);

  const handlePostCreated = (post) => {
    setNewPost(post);
  };

  return (
    <div>
      <h1>Post Management</h1>
      {token ? (
        <>
          <CreatePost token={token} onPostCreated={handlePostCreated} />
          {newPost && <PostList token={token} />}
          {selectedPostId && (
            <PostDetail token={token} postId={selectedPostId} />
          )}
        </>
      ) : (
        <p>Please log in to see the posts.</p>
      )}
    </div>
  );
};

export default App;
