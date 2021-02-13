package com.example.carpc.models;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.carpc.utils.AppConstants;

import java.util.Map;

import static com.example.carpc.utils.AppConstants.DATA_IP;
import static com.example.carpc.utils.AppConstants.DATA_PORT;

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
        return sharedPref.getString(DATA_IP, "192.168.1.90");
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

    public double getUsedAmpereHour() {
        int temp = sharedPref.getInt(AppConstants.DATA_BATTERY_USED_AH, 0);
        return temp != 0 ? (double) (temp / 10) : 0;
    }

    public double getUsedWattHour() {
        int temp = sharedPref.getInt(AppConstants.DATA_BATTERY_USED_WH, 0);
        return temp != 0 ? (double) (temp / 10) : 0;
    }

    public double getTotalAmpereHour() {
        int temp = sharedPref.getInt(AppConstants.DATA_BATTERY_CAPACITY_AH, 0);
        return temp != 0 ? (double) (temp / 10) : 0;
    }

    public double getTotalWattHour() {
        int temp = sharedPref.getInt(AppConstants.DATA_BATTERY_CAPACITY_WH, 0);
        return temp != 0 ? (double) (temp / 10) : 0;
    }

    public void setUsedAmpereHour(Double ampereHour) {
        sharedPref.edit().putInt(AppConstants.DATA_BATTERY_USED_AH, (int) (ampereHour * 10)).apply();

    }

    public void setUsedWattHour(Double ampereHour) {
        sharedPref.edit().putInt(AppConstants.DATA_BATTERY_USED_WH, (int) (ampereHour * 10)).apply();
    }

    public void commit() {
        sharedPref.edit().apply();

    }
}
