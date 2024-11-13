import axios from 'axios';

const API_URL = '/api/posts'; 


const getAuthTokenFromCookies = () => {
  // Split cookies string by ';' to get each individual cookie
  const cookies = document.cookie.split(';');

  // Iterate through cookies to find the one named 'authToken'
  for (let cookie of cookies) {
    // Split each cookie by '=' to separate name and value
    const [key, value] = cookie.trim().split('=');
    // Check if the name is 'authToken' and return its value
    if (key === 'authToken') {
      console.log("authToken found:", value);
      return value.trim();  // Return the trimmed value
    }
  }
  console.warn("authToken not found in cookies.");
  return null;  // Return null if not found
};


// Get authentication header (with 'Bearer' prefix for JWT tokens)
const getAuthHeader = () => {
  const token = getAuthTokenFromCookies();
  console.log("The token is:", token);
  return token ? { Authorization: `Bearer ${token}` } : {};
};
// Adjust this to your backend API URL

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
