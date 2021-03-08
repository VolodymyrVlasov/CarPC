package com.example.carpc.utils;

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

    // cmd = value

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

    CELL("cell"),
    CELLS_QUANTITY("cells");

    ;
    // &c100

    private String value;

    ParserKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

public class DataParser {
    private static DataParser instance = null;
    private EnumMap<ParserKey, String> parserData = new EnumMap<>(ParserKey.class);

    private DataParser() {

        // TODO: change init values
        for (ParserKey key : ParserKey.values()) {
            parserData.put(key, "0");
        }
        parserData.put(ParserKey.TEMP_SENSORS, ":0:0:0:0");
        parserData.put(ParserKey.LEVELS_MIN, "3000");
        parserData.put(ParserKey.LEVELS_MAX, "4200");
        parserData.put(ParserKey.LEVELS_ALLOWD, "3500");
        parserData.put(ParserKey.CELLS_QUANTITY, "36");
        parserData.put(ParserKey.CELL_INFO, "1:4000:200:150:5:8");
        parserData.put(ParserKey.ANALOG_INPUTS, ":1200:1350:500:150:553:887");
        parserData.put(ParserKey.CELL, "cell1: 4000:200:150:5:8");
    }

    public static DataParser getInstance() {
        if (instance == null) {
            instance = new DataParser();
        }
        return instance;
    }

    public DataParser parseInputData(String inputData) throws NullPointerException {
        if (inputData.contains("=")) {
            putConfigValue(inputData);
        } else if (inputData.contains("&")) {
            putActualValue(inputData);
        } else if (inputData.substring(0, 4).toLowerCase().contains(ParserKey.CELL.getValue())) {
            parserData.put(ParserKey.CELL, inputData);     // cell1: 4000,20,20,5,6
        }
        return this;
    }

    /** ??levels>?? cmin = 4200
     * cmd[0] -> cmin, cmd[1] -> 4200
     */
    private void putConfigValue(String inputData) {
        String[] cmd = inputData.substring(10).trim().split(" = ");
        for (ParserKey key : ParserKey.values()) {
            if (cmd[0].equals(key.getValue())) {
                parserData.put(key, cmd[1]);
                break;
            }
        }
    }

    /**&c500
     * &t:0:0:0:0
     * CURRETN, 500
     * TEMPSENSORS, :0:0:0:0
     */
    private void putActualValue(String inputData) {
        for (ParserKey key : ParserKey.values()) {
            if (inputData.substring(1, 3).contains(key.getValue())) {
                parserData.put(key, inputData.substring(2));
            }
        }
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

    public Double getTempSensorValue(int sensNumber) {
        String temp = parserData.get(ParserKey.TEMP_SENSORS);
        assert temp != null;
        String[] arr = temp.split(":");
        return Double.parseDouble(arr[sensNumber]);
    }

    public String getCurrentConfig() {
        return parserData.get(ParserKey.CONFIG_CURRENT);
    }

    public String getTransmittedData() {
        return parserData.get(ParserKey.CELL);
    }


    // TODO: make method non static
    public Integer getCellsQuantity(){
//        return 36;
        return Integer.parseInt(parserData.get(ParserKey.CELLS_QUANTITY));
    }

    public String getLevelsDataByCmdName(String keyName) {
        switch (keyName) {
            case "cmin":
                return parserData.get(ParserKey.LEVELS_CMIN);
            case "min":
                return parserData.get(ParserKey.LEVELS_MIN);
            case "max":
                return parserData.get(ParserKey.LEVELS_MAX);
            case "allowd":
                return parserData.get(ParserKey.LEVELS_ALLOWD);
            case "chrgd":
                return parserData.get(ParserKey.LEVELS_CHRGD);
            case "bal":
                return parserData.get(ParserKey.LEVELS_BAL);
            case "startbal":
                return parserData.get(ParserKey.LEVELS_STARTBAL);
            default:
                return "0";
        }
    }
}