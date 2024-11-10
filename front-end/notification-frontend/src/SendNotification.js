import React, { useState } from "react";
import { sendNotification } from "./NotificationService";

const SendNotification = () => {
  const [recipient, setRecipient] = useState("");
  const [message, setMessage] = useState("");
  const [type, setType] = useState("");
  const [error, setError] = useState("");

  const handleSendNotification = async () => {
    if (!recipient || !message || !type) {
      setError("All fields are required.");
      return;
    }
    try {
      const notification = await sendNotification(recipient, message, type);
      alert(`Notification sent to ${recipient}`);
      setRecipient("");
      setMessage("");
      setType("");
    } catch (err) {
      setError("Failed to send notification.");
    }
  };

  return (
    <div>
      <h2>Send Notification</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <div>
        <input
          type="text"
          placeholder="Recipient"
          value={recipient}
          onChange={(e) => setRecipient(e.target.value)}
        />
      </div>
      <div>
        <input
          type="text"
          placeholder="Message"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
        />
      </div>
      <div>
        <input
          type="text"
          placeholder="Type"
          value={type}
          onChange={(e) => setType(e.target.value)}
        />
      </div>
      <div>
        <button onClick={handleSendNotification}>Send Notification</button>
      </div>
    </div>
  );
};

export default SendNotification;
