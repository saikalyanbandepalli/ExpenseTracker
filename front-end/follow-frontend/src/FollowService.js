import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api/follow";

const getAuthHeader = () => {
  const token = localStorage.getItem("jwtToken"); // Assumes JWT is stored in localStorage
  return { Authorization: `Bearer ${token}` };
};

export const followUser = async (followerId, followeeId) => {
  try {
    const response = await axios.post(
      `${API_BASE_URL}/${followerId}/${followeeId}`,
      null,
      {
        headers: getAuthHeader(),
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error following user:", error);
    throw error;
  }
};

export const getFollowers = async (userId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/${userId}/followers`, {
      headers: getAuthHeader(),
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching followers:", error);
    throw error;
  }
};
