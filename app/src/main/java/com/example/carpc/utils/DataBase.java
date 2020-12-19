package com.example.carpc.utils;

import android.content.Context;

import com.example.carpc.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class DataBase {
//    private final String FILENAME;
//    private TreeMap<String, String> dataBaseValues;
//    private Context context;
//
//    public DataBase(String dataBaseName, Context context) {
//        FILENAME = dataBaseName;
//        dataBaseValues = new TreeMap<>();
//        this.context = context;
//
//        //TODO
//        // if file not found ->
//        //      create file with default values
//        //      createFile(String fileName)
//        // else ->
//        //      read file
//        //      readDataBase(String dataBaseName)
//
//        createFile(FILENAME);
//        readDataBase();
//    }
//
//    private void createFile(String fileName) {
//        Map<String, String> valueList = new TreeMap<>();
//        valueList.put("ip", "192.168.1.7");
//        valueList.put("port", "8080");
//        valueList.put("battery_total_capacity_a/h", "0");
//        valueList.put("used_battery_capacity_a/h", "0");
//        valueList.put("battery_total_capacity_wh", "0");
//        valueList.put("used_this_trip_battery_capacity_wh", "0");
//        valueList.put("used_this_charge_battery_capacity_wh", "0");
//        valueList.put("used_total_battery_capacity_wh", "0");
//        valueList.put("average_consumption_wh/km", "0");
//
//        File file = new File(context.getFilesDir(), "text");
//        if (!file.exists()) file.mkdir();
//        try {
//            File gpxfile = new File(file, fileName);
//            FileWriter writer = new FileWriter(gpxfile);
//
//            for (Map.Entry<String, String> e : valueList.entrySet()) {
//                writer.write(e.getKey() + "," + e.getValue() + "\n");
//            }
//            writer.flush();
//            writer.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void writeDataBase() {
//        File file = new File(MainActivity.getContext().getFilesDir(), "text");
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        try {
//            File gpxfile = new File(file, FILENAME);
//            FileWriter writer = new FileWriter(gpxfile);
//
//            for (Map.Entry<String, String> e : dataBaseValues.entrySet()) {
//                writer.write(e.getKey() + "," + e.getValue() + "\n");
//            }
//
//            writer.flush();
//            writer.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private TreeMap readDataBase() {
//
//        File path = new File(context.getFilesDir(), "text");
//        if (!path.exists()) {
//            path.mkdir();
//        }
//        File file = new File(path, FILENAME);
//        try (FileReader fr = new FileReader(file);
//             BufferedReader br = new BufferedReader(fr)) {
//            String st;
//
//            while ((st = br.readLine()) != null) {
//                String[] temp = st.split(",");
//                dataBaseValues.put(temp[0], (temp[1]));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return dataBaseValues;
//    }
//
//    //TODO getters
//    public TreeMap getAllValues() {
//        return (TreeMap) readDataBase();
//    }
//
//    public String getIP() {
//        return dataBaseValues.get("ip");
//    }
//
//    public Integer getPort() {
//        return Integer.parseInt(Objects.requireNonNull(
//                dataBaseValues.get("port")));
//    }
//
//    public double getBatteryTotalCapacityAH() {
//        return Double.parseDouble(Objects.requireNonNull(
//                dataBaseValues.get("battery_total_capacity_a/h")));
//    }
//
//    public double getUsedCapacityAH() {
//        return Double.parseDouble(Objects.requireNonNull(
//                dataBaseValues.get("used_battery_capacity_a/h")));
//    }
//
//    public double getBatteryTotalCapacityWH() {
//        return Double.parseDouble(Objects.requireNonNull(
//                dataBaseValues.get("battery_total_capacity_wh")));
//    }
//
//    public double getThisTripUsedCapacityWH() {
//        return Double.parseDouble(Objects.requireNonNull(
//                dataBaseValues.get("used_this_trip_battery_capacity_wh")));
//    }
//
//    public double getThisChargeUsedCapacityWH() {
//        return Double.parseDouble(Objects.requireNonNull(
//                dataBaseValues.get("used_this_charge_battery_capacity_wh")));
//    }
//
//    public double getTotalUsedBatteryCapacity() {
//        return Double.parseDouble(Objects.requireNonNull(
//                dataBaseValues.get("used_total_battery_capacity_wh")));
//    }
//
//    public int getConsumption() {
//        return Integer.parseInt(Objects.requireNonNull(
//                dataBaseValues.get("average_consumption_wh/km")));
//    }
//
//    //TODO setters
//    public void setIP(String value) {
//        dataBaseValues.put("ip", value);
//    }
//
//    public void setPort(int value) {
//        dataBaseValues.put("port", String.valueOf(value));
//    }
//
//    public void setBatteryTotalCapacityAH(String value) {
//        dataBaseValues.put("battery_total_capacity_a/h", value);
//    }
//
//    public void setUsedCapacityAH(String value) {
//        dataBaseValues.put("used_battery_capacity_a/h", value);
//    }
//
//    public void setBatteryTotalCapacityWH(String value) {
//        dataBaseValues.put("battery_total_capacity_wh", value);
//    }
//
//    public void setThisTripUsedCapacityWH(String value) {
//        dataBaseValues.put("used_this_trip_battery_capacity_wh", value);
//    }
//
//    public void setThisChargeUsedCapacityWH(String value) {
//        dataBaseValues.put("used_this_charge_battery_capacity_wh", value);
//    }
//
//    public void setTotalUsedBatteryCapacity(String value) {
//        dataBaseValues.put("used_total_battery_capacity_wh", value);
//    }
//
//    public void setConsumption(String value) {
//        dataBaseValues.put("average_consumption_wh/km", value);
//    }
}

