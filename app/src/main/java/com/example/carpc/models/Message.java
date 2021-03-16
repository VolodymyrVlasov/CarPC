package com.example.carpc.models;
import android.util.Log;

public class Message {
    private static final String TAG = "Message";
    private int messageCount;
    private boolean newMessageFlag = false;

    private final StringBuilder msg;

    public Message() {
        this.messageCount = 0;
        this.msg = new StringBuilder();
        msg.setLength(100);
    }

    public void setMessage(String message, boolean newMessage) {
        if (!message.equals("")){
            this.messageCount++;
            newMessageFlag = newMessage;
            this.msg.append(messageCount).append(":\t\t").append(message).append("\n");
        }
    }

    public String getMessage() {
        newMessageFlag = false;
        return msg.toString();
    }

    public boolean hasNewMessage() {
        return newMessageFlag;
    }

    public void clearAllText() {
        msg.delete(0, msg.length());
    }
}
