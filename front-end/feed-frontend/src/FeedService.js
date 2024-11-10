import axios from "axios";

// Base URL of the Feed service API
const API_BASE_URL = "http://localhost:8080/api/feed";

// Get authentication header (assuming you are using JWT tokens)
const getAuthHeader = () => {
  const token = localStorage.getItem("jwtToken"); // Assumes JWT is stored in localStorage
  return { Authorization: `Bearer ${token}` };
};

// Fetch user feed (posts) by userId
export const getUserFeed = async (userId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/${userId}`, {
      headers: getAuthHeader(),
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching user feed:", error);
    throw error;
  }
};
