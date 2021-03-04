package com.example.carpc.utils;

import java.util.EnumMap;

enum ParserKey {
    LEVELS_CMIN("cmin"),
    LEVELS_MIN("min"),
    LEVELS_MAX("max"),
    LEVELS_ALLOWD("allowd"),
    LEVELS_STARTBAL("startbal"),
    LEVELS_BAL("bal"),
    LEVELS_CHRGD("shrgd"),

    CONFIG_CURRENT("current"),
    CONFIG_IGNITION("ignition"),

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
    RPM("r");

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
    }

    public static DataParser getInstance() {
        if (instance == null) {
            instance = new DataParser();
        }
        return instance;
    }

    public DataParser parseInputData(String inputData) throws NullPointerException {

        for (ParserKey key : ParserKey.values()) {
            checkMultipleKeyForParse(key, inputData);
        }

        return this;
    }

    private void checkSingleKeyForParse(ParserKey key, String inputData) {
        if (inputData.contains(key.getValue())) {
            String value = inputData.substring(10).replaceAll(key.getValue() + " = ", "").trim();
            parserData.put(key, value);
        }
    }

    private void checkMultipleKeyForParse(ParserKey key, String inputData) {
        // todo проверить нет ли совпадений с названиями команд
        if (inputData.substring(1,3).contains(key.getValue())) {
            parserData.put(key, inputData.substring(2));
            System.out.println(key + ": " + parserData.get(key));
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

    public Double getFirstTempSensorValue() {
        String temp = parserData.get(ParserKey.TEMP_SENSORS);
        assert temp != null;
        String[] arr = temp.split(":");
        return Double.parseDouble(arr[1]);
    }

    public Double getSecondTempSensorValue() {
        String temp = parserData.get(ParserKey.TEMP_SENSORS);
        assert temp != null;
        String[] arr = temp.split(":");
        return Double.parseDouble(arr[2]);
    }

    public Double getThirdTempSensorValue() {
        String temp = parserData.get(ParserKey.TEMP_SENSORS);
        assert temp != null;
        String[] arr = temp.split(":");
        return Double.parseDouble(arr[3]);
    }

    public Double getFourthTempSensorValue() {
        String temp = parserData.get(ParserKey.TEMP_SENSORS);
        assert temp != null;
        String[] arr = temp.split(":");
        return Double.parseDouble(arr[4]);
    }

    public String getCurrentConfig() {
        return parserData.get(ParserKey.CONFIG_CURRENT);
    }


    // TODO: change keyNames as constants e.g cmin
    public String getLevelsDataByCmdName(String keyName) throws Exception {
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
                throw new Exception("Unknown level data key name exception!");
        }
    }
}