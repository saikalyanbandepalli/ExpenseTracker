import React, { useEffect, useState } from "react";
import { getAllNotifications, markAsRead } from "./NotificationService";

const NotificationList = () => {
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    fetchNotifications();
  }, []);

  const fetchNotifications = async () => {
    try {
      const notificationsData = await getAllNotifications();
      setNotifications(notificationsData);
    } catch (error) {
      console.error("Error fetching notifications:", error);
    }
  };

  const handleMarkAsRead = async (id) => {
    try {
      await markAsRead(id);
      fetchNotifications(); // Refresh the list after marking as read
    } catch (error) {
      console.error("Error marking notification as read:", error);
    }
  };

  return (
    <div>
      <h2>Notifications</h2>
      <ul>
        {notifications.length > 0 ? (
          notifications.map((notification) => (
            <li key={notification.id}>
              <div>
                <strong>{notification.message}</strong>
                <br />
                Type: {notification.type}
                <br />
                {notification.read ? (
                  <span>Read</span>
                ) : (
                  <button onClick={() => handleMarkAsRead(notification.id)}>
                    Mark as Read
                  </button>
                )}
              </div>
            </li>
          ))
        ) : (
          <li>No notifications</li>
        )}
      </ul>
    </div>
  );
};

export default NotificationList;
