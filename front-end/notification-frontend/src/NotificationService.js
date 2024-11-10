import axios from "axios";

const API_BASE_URL = "http://localhost:8080/notifications";

const getAuthHeader = () => {
  const token = localStorage.getItem("jwtToken"); // Assumes JWT is stored in localStorage
  return { Authorization: `Bearer ${token}` };
};

export const sendNotification = async (recipient, message, type) => {
  try {
    const response = await axios.post(
      `${API_BASE_URL}/send`,
      null,
      {
        params: { recipient, message, type },
        headers: getAuthHeader(),
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error sending notification:", error);
    throw error;
  }
};

export const getAllNotifications = async () => {
  try {
    const response = await axios.get(API_BASE_URL, {
      headers: getAuthHeader(),
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching notifications:", error);
    throw error;
  }
};

export const markAsRead = async (id) => {
  try {
    await axios.get(`${API_BASE_URL}/${id}/read`, {
      headers: getAuthHeader(),
    });
  } catch (error) {
    console.error("Error marking notification as read:", error);
    throw error;
  }
};
