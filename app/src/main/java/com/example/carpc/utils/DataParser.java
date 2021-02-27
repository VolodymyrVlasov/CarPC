package com.example.carpc.utils;

import java.util.HashMap;

public class DataParser {
    private static DataParser instance = null;

    public static String inputData;

    private static HashMap<String, Double> valueArray = new HashMap<>();
    private static HashMap<String, String> valueMinMaxCellArray = new HashMap<>();
    private static HashMap<String, String> valueMinMaxCellTempArray = new HashMap<>();
    private static HashMap<String, String> valueAnalogInputsArray = new HashMap<>();
    private static HashMap<String, String> valueTempSensorArray = new HashMap<>();
    private static HashMap<String, String> cellInfo = new HashMap<>();

    private static Boolean analogInputFlag = false, tempSensorFlag = false, capacityFlag = false,
            hasNewMessage = false;

    private String[] currentConfig = {"Please try again", "Please try again"};
    private String levelsMax = "", levelsMin = "", levelsStartbal = "", levelsBal = "",
            levelsChrgd = "0", levelsAllowd = "", levelsCmin = "";

    private DataParser() {
        String[] chars = new String[]{"V", "v", "l", "R", "q", "z", "w", "c", "F", "f", "d", "e", "r"};
        for (String e : chars) valueArray.put(e, 0.0);
        valueMinMaxCellArray.put("n", "0:0.000");
        valueMinMaxCellArray.put("m", "0:0.000");
        valueAnalogInputsArray.put("i", "0:0:0:0:0:0");
        valueTempSensorArray.put("t", "0:0:0:0");
        valueMinMaxCellTempArray.put("w", "0:0.000");
        valueMinMaxCellTempArray.put("z", "0:0.000");
        cellInfo.put("q", "0:0000:000:000:0:1");
    }

    public static DataParser getInstance() {
        if (instance == null) {
            instance = new DataParser();
        }

        return instance;
    }

    public DataParser parseInputData(String inputData) throws NullPointerException {
        DataParser.inputData = inputData;
        hasNewMessage = true;

        try {
            if (inputData.substring(1, 2).equals("m") || inputData.substring(1, 2).equals("n")) {
                //SET FLAGS
                analogInputFlag = false;
                //PUT NEW DATA
                valueMinMaxCellArray.put(inputData.substring(1, 2), inputData.substring(2));
            }
            if (inputData.substring(1, 2).equals("w") || inputData.substring(1, 2).equals("z")) {
                analogInputFlag = false;
                valueMinMaxCellTempArray.put(inputData.substring(1, 2),
                        inputData.substring(2));
            }
            if (inputData.substring(1, 3).equals("i:")) {
                analogInputFlag = true;
                valueAnalogInputsArray.put(inputData.substring(1, 2),
                        inputData.substring(3));
            }
            if (inputData.substring(1, 3).equals("t:")) {
                analogInputFlag = false;
                valueTempSensorArray.put(inputData.substring(1, 2),
                        inputData.substring(3));
            }
            if (inputData.substring(1, 2).equals("q")) {
                analogInputFlag = false;
                cellInfo.put(inputData.substring(1, 2),
                        inputData.substring(3));
            }
            if (inputData.substring(1, 2).equals("l")) {
                capacityFlag = true;
            }
            if (inputData.contains("current")) {
                currentConfig = inputData.split("current");
            }
            if (inputData.contains("cmin")) {
                levelsCmin = inputData.substring(10);
                levelsCmin = levelsCmin.replaceAll("cmin = ", "").trim();
                inputData = null;
            }
            if (inputData.contains("max")) {
                levelsMax = inputData.substring(10);
                levelsMax = levelsMax.replaceAll("max = ", "").trim();
            }
            if (inputData.contains("min")) {
                levelsMin = inputData.substring(10);
                levelsMin = levelsMin.replaceAll("min = ", "").trim();
            }
            if (inputData.contains("startbal")) {
                levelsStartbal = inputData.substring(10);
                levelsStartbal = levelsStartbal.replaceAll("startbal = ", "").trim();
            }
            if (inputData.contains("bal")) {
                levelsBal = inputData.substring(10);
                levelsBal = levelsBal.replaceAll("bal = ", "").trim();
            }
            if (inputData.contains("chrgd")) {
                levelsChrgd = inputData.substring(10);
                levelsChrgd = levelsChrgd.replaceAll("chrgd = ", "").trim();
            }
            if (inputData.contains("allowd")) {
                levelsAllowd = inputData.substring(10);
                levelsAllowd = levelsAllowd.replaceAll("allowd = ", "").trim();
            }
            if (!inputData.substring(1, 2).equals("q") &&
                    !inputData.substring(1, 3).equals("t:") &&
                    !inputData.substring(1, 3).equals("i:") &&
                    !inputData.substring(1, 2).equals("w") &&
                    !inputData.substring(1, 2).equals("z") &&
                    !inputData.substring(1, 2).equals("m") &&
                    !inputData.substring(1, 2).equals("n") &&
                    !inputData.substring(1, 2).equals("E")) {
                analogInputFlag = false;
                valueArray.put(inputData.substring(1, 2),
                        Double.parseDouble(inputData.substring(2)));
            }
        } catch (NullPointerException | StringIndexOutOfBoundsException | NumberFormatException e) {
        }

        return this;
    }


    public Integer getSpeed() {
        // todo: can be errors
        return (int) Math.round(valueArray.get("V"));
    }

    public Double getCurrent() {
        return valueArray.get("c") / 10;
    }

    public Double getVoltage() {
        return valueArray.get("v") / 10;
    }

    public Double getTotalDistance() {
        return valueArray.get("F") / 10;
    }

    public Double getLastChargePassedDistance() {
        return valueArray.get("f") / 10;
    }

    public Double getRange() {
        return valueArray.get("R") / 10;
    }

    public Double getBatteryCapacity() {
        return valueArray.get("l") / 10;
    }

    public Double getMotorTemperature() {
        return valueArray.get("d") / 10;
    }

    public Double getInverterTemperature() {
        return valueArray.get("e") / 10;
    }

    public Double getRPM() {
        return valueArray.get("r");
    }

    public String getMinCellVoltage() {
        return valueMinMaxCellArray.get("n");
    }

    public String getMaxCellVoltage() {
        return valueMinMaxCellArray.get("m");
    }

    public String getMinCellTemp() {
        return valueMinMaxCellTempArray.get("w");
    }

    public String getMaxCellTemp() {
        return valueMinMaxCellTempArray.get("z");
    }

    public String getAnalogInputsValue() {
        return valueAnalogInputsArray.get("i");
    }

    public String getCellInfo() {
        return cellInfo.get("q");
    }

    public Double getFirstTempSensorValue() {
        String temp = valueTempSensorArray.get("t");
        assert temp != null;
        String[] arr = temp.split(":");
        return Double.parseDouble(arr[0]);
    }

    public Double getSecondTempSensorValue() {
        String temp = valueTempSensorArray.get("t");
        assert temp != null;
        String[] arr = temp.split(":");
        return Double.parseDouble(arr[1]);
    }

    public Double getThirdTempSensorValue() {
        String temp = valueTempSensorArray.get("t");
        assert temp != null;
        String[] arr = temp.split(":");
        return Double.parseDouble(arr[2]);
    }

    public Double getFourthTempSensorValue() {
        String temp = valueTempSensorArray.get("t");
        assert temp != null;
        String[] arr = temp.split(":");
        return Double.parseDouble(arr[3]);
    }

    public Boolean analogInputValuesChanged() {
        return analogInputFlag;
    }

    public Boolean tempSensorsValuesChanged() {
        return tempSensorFlag;
    }

    public Boolean isCapacityChanged() {
        return capacityFlag;
    }

    public String getCurrentConfig() {
        if (currentConfig[1].contains("try")) {
            currentConfig[1] = currentConfig[0];
        } else {
            currentConfig[1] = currentConfig[1].substring(3);
        }
        return currentConfig[1];
    }


    // TODO: change keyN?ames as constants e.g cmin
    public String getLevelsDataByCmdName(String keyName) throws Exception {
        switch (keyName) {
            case "cmin":
                return levelsCmin;
            case "min":
                return levelsMin;
            case "max":
                return levelsMax;
            case "allowd":
                return levelsAllowd;
            case "chrgd":
                return levelsChrgd;
            case "bal":
                return levelsBal;
            case "startbal":
                return levelsStartbal;
            default:
                throw new Exception("Unknown level data key name exception!");
        }
    }
}