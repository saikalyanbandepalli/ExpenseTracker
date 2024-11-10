import axios from "axios";

// Base URL of the Comment service API
const API_BASE_URL = "http://localhost:8080/api/comments";

// Get authentication header (assuming JWT token)
const getAuthHeader = () => {
  const token = localStorage.getItem("jwtToken"); // Assumes JWT is stored in localStorage
  return { Authorization: `Bearer ${token}` };
};

// Add a comment to a post by userId
export const addComment = async (userId, comment) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/${userId}`, comment, {
      headers: getAuthHeader(),
    });
    return response.data;
  } catch (error) {
    console.error("Error adding comment:", error);
    throw error;
  }
};

// React to a comment (by postId and userId)
export const reactToComment = async (postId, userId, reaction) => {
  try {
    const response = await axios.post(
      `${API_BASE_URL}/${postId}/react/${userId}`,
      reaction,
      {
        headers: getAuthHeader(),
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error reacting to comment:", error);
    throw error;
  }
};
