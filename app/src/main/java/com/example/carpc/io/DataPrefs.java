package com.example.carpc.io;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.carpc.constants.AppConstants;

import java.util.Map;

import static com.example.carpc.constants.AppConstants.DATA_IP;
import static com.example.carpc.constants.AppConstants.DATA_PORT;

public class DataPrefs {
    private SharedPreferences sharedPref;
    private static DataPrefs instance = null;

    public static DataPrefs getInstance(Context context) {
        if (instance == null) {
            instance = new DataPrefs(context);
        }

        return instance;
    }

    public DataPrefs(Context context) {
        sharedPref = context.getSharedPreferences(
                AppConstants.DATAFILE, Context.MODE_PRIVATE);
    }

    public String getIP() {
        return sharedPref.getString(DATA_IP, "192.168.1.7");
    }

    public void setIP(String IP) {
        sharedPref.edit().putString(AppConstants.DATA_IP, IP);
    }

    public int getPort() {
        return sharedPref.getInt(DATA_PORT, 8080);
    }

    public void setPort(int port) {
        sharedPref.edit().putInt(AppConstants.DATA_PORT, port);
    }

    public Map<String, ?> GetAll() {
        return sharedPref.getAll();
    }
}
