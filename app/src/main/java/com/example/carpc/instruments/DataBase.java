package com.example.carpc.instruments;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class DataBase  {
    Map<String, Integer> guestList = new TreeMap<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void readFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {
            String st;

            while ((st = br.readLine()) != null) {
                String[] temp = st.split(",");
                guestList.put(temp[0], Integer.parseInt(temp[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void writeToFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileWriter myWriter = new FileWriter(fileName);) {
            for (Map.Entry<String, Integer> e : guestList.entrySet()){
                myWriter.write(e.getKey() + "," + e.getValue() + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

