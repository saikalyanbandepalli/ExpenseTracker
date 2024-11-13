import axios from "axios";

// Base URL of the Feed service API
const API_BASE_URL = "http://localhost:8086/api/feed/getFeed";

// Function to get JWT token from cookies
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

// Fetch user feed (posts) by userId
export const getUserFeed = async () => {
  try {
    // Send GET request to fetch the user feed
    const response = await axios.get(API_BASE_URL, {
      headers: getAuthHeader(),
     // withCredentials: true  // Ensure cookies are sent with the request
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching user feed:", error);
    throw error;
  }
};
