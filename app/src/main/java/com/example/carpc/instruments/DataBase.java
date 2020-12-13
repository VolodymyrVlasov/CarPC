package com.example.carpc.instruments;

import com.example.carpc.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class DataBase {
    public final String UNSUBSCRIBE = "@a0";
    public final String SUBSCRIBE = "@a1";
    private TreeMap<String, String> dataBaseValues = new TreeMap<>();


    public void createFileWithDefaultValues(String fileName) {

        Map<String, String> valueList = new TreeMap<>();
        valueList.put("AH", "15");
        valueList.put("WH THIS TRIP", "100");
        valueList.put("WH TOTAL TRIP", "0");
        valueList.put("WH THIS CHARGE", "2000");
        valueList.put("AVG CONSUMPTION", "160");
        valueList.put("AVG SPEED", "0");
        valueList.put("TRIP SERVICE", "0");
        valueList.put("IP", "192.168.1.90");
        valueList.put("PORT", "8080");
        valueList.put("SSID", "PORSCHE924EV");


        File file = new File(MainActivity.getContext().getFilesDir(), "text");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File gpxfile = new File(file, fileName);
            FileWriter writer = new FileWriter(gpxfile);

            for (Map.Entry<String, String> e : valueList.entrySet()) {
                writer.write(e.getKey() + "," + e.getValue() + "\n");
            }

            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeDataBase(String dataBaseName) {
        File file = new File(MainActivity.getContext().getFilesDir(), "text");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File gpxfile = new File(file, dataBaseName);
            FileWriter writer = new FileWriter(gpxfile);

            for (Map.Entry<String, String> e : dataBaseValues.entrySet()) {
                writer.write(e.getKey() + "," + e.getValue() + "\n");
            }

            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private TreeMap readDataBase(String dataBaseName) {
        Map<String, String> readValueList = new TreeMap<>();

        File path = new File(MainActivity.getContext().getFilesDir(), "text");
        if (!path.exists()) {
            path.mkdir();
        }
        File file = new File(path, dataBaseName);
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {
            String st;

            while ((st = br.readLine()) != null) {
                String[] temp = st.split(",");
                readValueList.put(temp[0], (temp[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (TreeMap) readValueList;
    }
}

