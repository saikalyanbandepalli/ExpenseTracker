import axios from 'axios';

const API_URL = '/api/posts'; // Adjust this to your backend API URL

// Get all posts for the logged-in user
export const getAllPosts = (token) => {
  return axios.get(API_URL, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

// Create a new post
export const createPost = (token, post) => {
  return axios.post(API_URL, post, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

// Get a single post by ID
export const getPostById = (token, postId) => {
  return axios.get(`${API_URL}/${postId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

// Update a post
export const updatePost = (token, postId, post) => {
  return axios.put(`${API_URL}/${postId}`, post, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

// Delete a post
export const deletePost = (token, postId) => {
  return axios.delete(`${API_URL}/${postId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};
