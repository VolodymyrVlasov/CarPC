package com.example.carpc.utils;

import com.example.carpc.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

//TODO Create methods for:
// - return ampere hour
// - return watt hour
// - return trip distance (current trip view)
// - return average consumption
// - return average speed

public class Counter {
    private static DataParser parser;
    private static final long DELAY = 10L;
    private static final long PERIOD = 10L;
    private static double ampereHour;
    private static double wattHour;

    public Counter() {
        parser = (DataParser) MainActivity.getParser();
        update();
    }

    public static double getUsedAH() {
        return ampereHour;
    }

    public static double getUsedWH() {
        return wattHour;
    }

    public static void setUsedAH(double ampereHour) {
        Counter.ampereHour = ampereHour;
    }

    public static void setUsedWH(double wattHour) {
        Counter.wattHour = wattHour;
    }

    public void update() {
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                ampereHour = ampereHour + (parser.getCurrent() / 3600) / 100;
                wattHour = wattHour + (parser.getCurrent() * parser.getVoltage() / 3600) / 100000;
            }
        };
        new Timer().scheduleAtFixedRate(repeatedTask, DELAY, PERIOD);
    }
}
