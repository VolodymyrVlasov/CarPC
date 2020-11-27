package com.example.carpc.instruments;

import java.util.HashMap;

public class DataParser {


    public static String inputData;

    private static HashMap<String, Double> valueArray = new HashMap<>();
    private static HashMap<String, String> valueMinMaxCellArray = new HashMap<>();
    private static HashMap<String, String> valueMinMaxCellTempArray = new HashMap<>();
    private static HashMap<String, String> valueAnalogInputsArray = new HashMap<>();
    private static HashMap<String, String> valueTempSensorArray = new HashMap<>();
    private static HashMap<String, String> cellInfo = new HashMap<>();

    private static Boolean analogInputFlag = false, tempSensorFlag = false, capacityFlag = false;

    private String currentConfig = "Please try again";


    public DataParser() {
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

    public void parseInputData(String inputData) throws NullPointerException {
        System.out.println("Received message:" + inputData);
        if (inputData.contains("current")) {

            System.out.println("????????parseInputData:" );
        }

        try {

            if (inputData.substring(1, 2).equals("m") || inputData.substring(1, 2).equals("n")) {
                //SET FLAGS
                analogInputFlag = false;
                //PUT NEW DATA
                valueMinMaxCellArray.put(inputData.substring(1, 2),
                        inputData.substring(2));
            } else if (inputData.substring(1, 2).equals("w") || inputData.substring(1, 2).equals("z")) {
                analogInputFlag = false;
                valueMinMaxCellTempArray.put(inputData.substring(1, 2),
                        inputData.substring(2));
            } else if (inputData.substring(1, 3).equals("i:")) {
                analogInputFlag = true;
                valueAnalogInputsArray.put(inputData.substring(1, 2),
                        inputData.substring(3));
            } else if (inputData.substring(1, 3).equals("t:")) {
                analogInputFlag = false;
                valueTempSensorArray.put(inputData.substring(1, 2),
                        inputData.substring(3));
            } else if (inputData.substring(1, 3).equals("q")) {
                analogInputFlag = false;
                cellInfo.put(inputData.substring(1, 2),
                        inputData.substring(3));
            } else if (inputData.substring(1, 3).equals("l")) {
                capacityFlag = true;
            } else if (inputData.contains("current")) {
                currentConfig = inputData;
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
    }

    public static String getInputMessage() {
        return inputData != null ? inputData : ">";
    }

    public Integer getSpeed() {
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
        return currentConfig;
    }

}