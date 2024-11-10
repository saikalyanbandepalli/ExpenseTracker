import React from "react";
import NotificationList from "./NotificationList";
import SendNotification from "./SendNotification";

const App = () => {
  return (
    <div>
      <h1>Notification System</h1>
      <SendNotification />
      <NotificationList />
    </div>
  );
};

export default App;
