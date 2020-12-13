package com.example.carpc.instruments;
import android.util.Log;

public class Message {
    private static final String TAG = "Message";
    private String message;
    private int messageCount;
    private boolean newMessageFlag = false;

    private StringBuilder msg;

    public Message() {
        this.messageCount = 0;
        this.msg = new StringBuilder();
        msg.setLength(100);
    }

    public void setMessage(String message) {
        this.message = message;
        this.messageCount++;
        Log.i(TAG, getCountMessage());
        newMessageFlag = true;
    }

    public void setMessage(String message, boolean newMessage) {
        if (!message.equals("")){
            this.messageCount++;
            newMessageFlag = newMessage;
            this.msg.append(messageCount + ":\t\t" + message + "\n");
        }
    }

    public String getMessage() {
        newMessageFlag = false;
        return msg.toString();
    }

    public int getMessageCount() {
        return messageCount;
    }

    public String getCountMessage() {
        return messageCount + " " + message;
    }

    public boolean hasNewMessage() {
        return newMessageFlag;
    }

    public void clearAllText() {
        msg.delete(0, msg.length());
    }

}
