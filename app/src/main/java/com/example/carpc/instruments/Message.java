package com.example.carpc.instruments;

import android.util.Log;

import com.example.carpc.MainActivity;
import com.example.carpc.widgets.settingsScreen.tabs.TerminalTab;

public class Message {
    private static final String TAG = "Message";
    private String message;
    private int messageCount = 0;
    private boolean newMessageFlag = false;

    public void setMessage(String message) {
        this.message = message;
        this.messageCount++;
        Log.i(TAG, getCountMessage());
        newMessageFlag = true;
        System.out.println(getCountMessage());
    }

    public String getMessage() {
        newMessageFlag = false;
        return message;
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

}
