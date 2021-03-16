package com.example.carpc.utils;

import android.util.Log;

import java.util.EnumMap;

enum ParserKey {
    // Config values
    // Levels path
    LEVELS_CMIN("cmin"),
    LEVELS_MIN("min"),
    LEVELS_MAX("max"),
    LEVELS_ALLOWD("allowd"),
    LEVELS_STARTBAL("startbal"),
    LEVELS_BAL("bal"),
    LEVELS_CHRGD("chrgd"),
    // Config path
    CONFIG_CURRENT("current"),
    CONFIG_IGNITION("ignition"),
    CONFIG_POWER("power"),
    CONFIG_PRECHARGE("precharge"),
    CONFIG_MAIN_CONTACTOR("maincontactor"),
    CONFIG_CHARGE_CURRENT_MAX("chcurrentmax"),
    CONFIG_DISCHARGE_CURRENT_MAX("discurrentmax"),
    CONFIG_THERMOSTAT_1("thermostat1"),
    CONFIG_THERMOSTAT_2("thermostat2"),
    CONFIG_THERMOSTAT_3("thermostat3"),
    CONFIG_THERMOSTAT_4("thermostat4"),
    CONFIG_TEMP_SENS_TYPE("temptypes"),
    CONFIG_SPEED("speed"),
    CONFIG_RPM("rpm"),
    CONFIG_BATTERY_CAPACITY("battery"),
    CONFIG_CHARGING_TEMP("chtemp"),
    CONFIG_DISCHARGING_TEMP("pwrtemp"),
    CONFIG_CAN("can"),
    CONFIG_ACCELERATOR("accell"),
    CONFIG_ALARM_BEEPER("alarm"),
    CONFIG_CELLS_QUANTITY("cells"),

    // Actual values
    SPEED("V"),
    MAX_CELL_VOLT("m"),
    MIN_CELL_VOLT("n"),
    MAX_CELL_TEMP("z"),
    MIN_CELL_TEMP("w"),
    CELL_INFO("q"),
    ANALOG_INPUTS("i"),
    OPTO_INPUTS("o"),
    BUTTONS("b"),
    TEMP_SENSORS("t"),
    ERROR("E"),
    CURRENT("c"),
    VOLTAGE("v"),
    STATUS("s"),
    TOTAL_DISTANCE("F"),
    LAST_CHARGE_DISTANCE("f"),
    RANGE("R"),
    CAPACITY("l"),
    MOTOR_TEMP("d"),
    INVERTOR_TEMP("e"),
    RPM("r"),
    CELL("cell");

    private String value;

    ParserKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

public class DataParser {
    private String TAG = "DATA_PARSER";
    private static DataParser instance = null;
    private static EnumMap<ParserKey, String> parserData = new EnumMap<>(ParserKey.class);

    private DataParser() {
        for (ParserKey key : ParserKey.values()) {
            parserData.put(key, "0");
        }
    }

    public static DataParser getInstance() {
        if (instance == null) {
            instance = new DataParser();
        }
        return instance;
    }

    public DataParser parseInputData(String inputData) throws NullPointerException {
        Log.i(TAG + "_INPUT_DATA", "inputData : " + inputData);
        if (inputData.contains("&")) {
            Log.i(TAG + "_INPUT_DATA_CONTAIN", "\"&\" -> putActualValue()");
            putActualValue(inputData);
        } else if (inputData.contains("=")) {
            Log.i(TAG + "_INPUT_DATA_CONTAIN", "\"=\" -> putConfigValue()");
            putConfigValue(inputData);
        } else if (inputData.toLowerCase().contains("cell")) {
            Log.i(TAG + "_INPUT_DATA_CONTAIN", "\"cell\" -> parserData.put(ParserKey.CELL, inputData)");
            parserData.put(ParserKey.CELL, inputData);
        }
        return this;
    }

    private void putConfigValue(String inputData) {
        String[] cmd = inputData.substring(10).trim().split(" = ");
        for (ParserKey key : ParserKey.values()) {
            if (cmd[0].equals(key.getValue())) {
                parserData.put(key, cmd[1]);
                Log.i(TAG + "_CONFIG_VALUE", key + ": " + cmd[1]);
                break;
            }
        }
    }

    private void putActualValue(String inputData) {
        for (ParserKey key : ParserKey.values()) {
            if (inputData.substring(1, 3).contains(key.getValue())) {
                parserData.put(key, inputData.substring(2));
                Log.i(TAG + "_ACTUAL_VALUE", key + ": " + inputData.substring(2));
            }
        }
    }

    public Integer getMaxConfigVoltage() {
        return Integer.parseInt(parserData.get(ParserKey.LEVELS_MAX)) != 0 ? Integer.parseInt(parserData.get(ParserKey.LEVELS_MAX)) : 4200;
    }

    public Integer getSpeed() {
        return Integer.valueOf(parserData.get(ParserKey.SPEED));
    }

    public Double getCurrent() {
        return Double.parseDouble(parserData.get(ParserKey.CURRENT)) / 10;
    }

    public Double getVoltage() {
        return Double.parseDouble(parserData.get(ParserKey.VOLTAGE)) / 10;
    }

    public Double getTotalDistance() {
        return Double.parseDouble(parserData.get(ParserKey.TOTAL_DISTANCE)) / 10;
    }

    public Double getLastChargePassedDistance() {
        return Double.parseDouble(parserData.get(ParserKey.LAST_CHARGE_DISTANCE)) / 10;
    }

    public Double getRange() {
        return Double.parseDouble(parserData.get(ParserKey.RANGE)) / 10;
    }

    public Double getBatteryCapacity() {
        return Double.parseDouble(parserData.get(ParserKey.CAPACITY)) / 10;
    }

    public Double getMotorTemperature() {
        return Double.parseDouble(parserData.get(ParserKey.MOTOR_TEMP)) / 10;
    }

    public Double getInverterTemperature() {
        return Double.parseDouble(parserData.get(ParserKey.INVERTOR_TEMP)) / 10;
    }

    public Double getRPM() {
        return Double.parseDouble(parserData.get(ParserKey.RPM)) / 10;
    }

    public String getMinCellVoltage() {
        return parserData.get(ParserKey.MIN_CELL_VOLT);
    }

    public String getMaxCellVoltage() {
        return parserData.get(ParserKey.MIN_CELL_VOLT);
    }

    public String getMinCellTemp() {
        return parserData.get(ParserKey.MIN_CELL_TEMP);
    }

    public String getMaxCellTemp() {
        return parserData.get(ParserKey.MAX_CELL_TEMP);
    }

    public String getAnalogInputsValue() {
        return parserData.get(ParserKey.ANALOG_INPUTS);
    }

    public String getCellInfo() {
        return parserData.get(ParserKey.CELL_INFO);
    }

    public Double getTempSensorValue(int sensorNumber) {
        String temp = parserData.get(ParserKey.TEMP_SENSORS);
        assert temp != null;
        String[] arr = temp.split(":");
        return Double.parseDouble(arr[sensorNumber]) / 10;
    }

    public String getTransmittedData() {
        return parserData.get(ParserKey.CELL);
    }

    public Integer getCellsQuantity() {
        return Integer.parseInt(parserData.get(ParserKey.CONFIG_CELLS_QUANTITY)) != 0 ?
                Integer.parseInt(parserData.get(ParserKey.CONFIG_CELLS_QUANTITY)) :
                34;
    }

    public String getConfigDataByCmdName(String keyName) {
        String data = "0";
        for (ParserKey key : ParserKey.values()) {
            if (key.getValue().equals(keyName)) {
                data = parserData.get(key);
                break;
            }
        }
        return data;
    }

    public String getLevelsDataByCmdName(String keyName) {
        switch (keyName) {
            case "cmin":
                return parserData.get(ParserKey.LEVELS_CMIN);
            case "min":
                return parserData.get(ParserKey.LEVELS_MIN).equals("0") ? "3000" : parserData.get(ParserKey.LEVELS_MIN);
            case "max":
                return parserData.get(ParserKey.LEVELS_MAX).equals("0") ? "4200" : parserData.get(ParserKey.LEVELS_MAX);
            case "allowd":
                return parserData.get(ParserKey.LEVELS_ALLOWD).equals("0") ? "3500" : parserData.get(ParserKey.LEVELS_ALLOWD);
            case "chrgd":
                return parserData.get(ParserKey.LEVELS_CHRGD);
            case "bal":
                return parserData.get(ParserKey.LEVELS_BAL);
            case "startbal":
                return parserData.get(ParserKey.LEVELS_STARTBAL);
            case "cells":
                return parserData.get(ParserKey.CONFIG_CELLS_QUANTITY);
            default:
                return "0";
        }
    }
}