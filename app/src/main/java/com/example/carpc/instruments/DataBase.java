package com.example.carpc.instruments;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.carpc.MainActivity;
import com.google.android.material.internal.ContextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import static java.security.AccessController.getContext;

public class DataBase {


//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public TreeMap readFile(String fileName) {
////        File file = new File(fileName);
////        if (!file.exists()) {
////            createFileWithDefaultValues(fileName);
////        }
////
////        try (FileReader fr = new FileReader(file);
////             BufferedReader br = new BufferedReader(fr)) {
////            String st;
////
////            while ((st = br.readLine()) != null) {
////                String[] temp = st.split(",");
////                valueList.put(temp[0], (temp[1]));
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        return (TreeMap) valueList;
//    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public void writeToFile(String fileName) {
////        File file = new File(fileName);
////        if (!file.exists()) {
////            createFileWithDefaultValues(fileName);
////        }
////
////        try (FileWriter myWriter = new FileWriter(fileName);) {
////            for (Map.Entry<String, String> e : valueList.entrySet()) {
////                myWriter.write(e.getKey() + "," + e.getValue() + "\n");
////            }
////        } catch (IOException e) {
////            System.out.println("An error occurred.");
////            e.printStackTrace();
////        }
//    }

//    public void createFileWithDefaultValues(String fileName) {

//        File file = new File("/data/CarPC/DATA_BASE.txt");
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        valueList.put("AH", "0");
//        valueList.put("WH THIS TRIP", "0");
//        valueList.put("WH TOTAL TRIP", "0");
//        valueList.put("WH THIS CHARGE", "0");
//        valueList.put("AVG CONSUMPTION", "160");
//        valueList.put("AVG SPEED", "0");
//
//        valueList.put("TRIP SERVICE", "0");
//
//        valueList.put("IP", "192.168.1.7");
//        valueList.put("PORT", "8000");
//        valueList.put("SSID", "PORSCHE924EV");

        //writeToFile(fileName);

//    }
}

