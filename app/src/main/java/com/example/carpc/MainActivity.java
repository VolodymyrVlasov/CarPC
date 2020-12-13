package com.example.carpc;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.carpc.instruments.DataParser;
import com.example.carpc.instruments.Message;
import com.example.carpc.widgets.chargeScreen.ChargeWidget;
import com.example.carpc.widgets.dashboardScreen.DashboardWidget;
import com.example.carpc.widgets.settingsScreen.SettingsWidget;
import com.example.carpc.widgets.settingsScreen.tabs.TerminalTab;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "myLogs";
    private static DataParser parser;

    private DashboardWidget dashboardWidget;
    private static SettingsWidget settingsWidget;
    private ChargeWidget chargeWidget;
    private androidx.fragment.app.FragmentTransaction fTrans;
    private static Message message;


    public static Context getContext() {
        return getContext();
    }

//    TreeMap<String, String> dataBaseValues = new TreeMap<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parser = new DataParser();
        dashboardWidget = new DashboardWidget();
        settingsWidget = new SettingsWidget();
        chargeWidget = new ChargeWidget();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.frgmCont, dashboardWidget);
        fTrans.commit();
        message = new Message();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
//        for (Map.Entry<String, String> e : dataBaseValues.entrySet()) {
//            System.out.println(e.getKey() + " - " + e.getValue());
//        }
//        dataBaseValues.put("AH", "25252525");
//        writeDataBase("DATA_BASE.txt");
//        readDataBase("DATA_BASE.txt");
//        for (Map.Entry<String, String> e : dataBaseValues.entrySet()) {
//            System.out.println(e.getKey() + " - " + e.getValue());
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View v) {
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        switch (v.getId()) {
            case R.id.btnCharge:
                if (!chargeWidget.isVisible()) fTrans.replace(R.id.frgmCont, chargeWidget);
                break;
            case R.id.btnDashboard:
                if (!dashboardWidget.isVisible()) fTrans.replace(R.id.frgmCont, dashboardWidget);
                break;
            case R.id.btnSettings:
                if (!settingsWidget.isVisible()) fTrans.replace(R.id.frgmCont, settingsWidget);
                break;
        }
        fTrans.commit();
    }

//    public void createFileWithDefaultValues(String fileName) {
//
//        Map<String, String> valueList = new TreeMap<>();
//        valueList.put("AH", "15");
//        valueList.put("WH THIS TRIP", "100");
//        valueList.put("WH TOTAL TRIP", "0");
//        valueList.put("WH THIS CHARGE", "2000");
//        valueList.put("AVG CONSUMPTION", "160");
//        valueList.put("AVG SPEED", "0");
//        valueList.put("TRIP SERVICE", "0");
//        valueList.put("IP", "192.168.1.90");
//        valueList.put("PORT", "8080");
//        valueList.put("SSID", "PORSCHE924EV");
//
//
//        File file = new File(MainActivity.this.getFilesDir(), "text");
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        try {
//            File gpxfile = new File(file, fileName);
//            FileWriter writer = new FileWriter(gpxfile);
//
//            for (Map.Entry<String, String> e : valueList.entrySet()) {
//                writer.write(e.getKey() + "," + e.getValue() + "\n");
//            }
//
//            writer.flush();
//            writer.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void writeDataBase(String dataBaseName) {
//        File file = new File(MainActivity.this.getFilesDir(), "text");
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        try {
//            File gpxfile = new File(file, dataBaseName);
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
//    private TreeMap readDataBase(String dataBaseName) {
//        Map<String, String> readValueList = new TreeMap<>();
//
//        File path = new File(MainActivity.this.getFilesDir(), "text");
//        if (!path.exists()) {
//            path.mkdir();
//        }
//        File file = new File(path, dataBaseName);
//        try (FileReader fr = new FileReader(file);
//             BufferedReader br = new BufferedReader(fr)) {
//            String st;
//
//            while ((st = br.readLine()) != null) {
//                String[] temp = st.split(",");
//                readValueList.put(temp[0], (temp[1]));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return (TreeMap) readValueList;
//    }
//
//    public void startCommunicationWithNewParams(final String newAddress, final int newPort) {
//        address = newAddress;
//        port = newPort;
//        flagAutoConnect = true;
//        startCommunication(newAddress, newPort);
//    }
//
//    public static void setParameters(String inputData) throws NullPointerException {
////        if (settingsWidget.isVisible() && TerminalTab.updateFlag) {
////            terminalTab.showNewMessage(inputData);
////        }
//        if (batteryManagerWidget.isAdded()) {
//            batteryManagerWidget.updateParameters(
//                    parser.getVoltage(),
//                    parser.getCurrent(),
//                    parser.getBatteryCapacity(),
//                    parser.getMinCellVoltage(),
//                    parser.getMaxCellVoltage());
//        }
//        if (speedometerWidget.isAdded()) {
//            speedometerWidget.updateSpeed(
//                    parser.getSpeed());
//        }
//        if (tripManagerWidget.isAdded()) {
//            tripManagerWidget.updateParameters(
//                    parser.getLastChargePassedDistance(),
//                    parser.getRange(),
//                    parser.getTotalDistance());
//        }
//        if (iconStatusRightWidget.isAdded() && parser.analogInputValuesChanged()) {
//            iconStatusRightWidget.updateParameters(
//                    parser.getAnalogInputsValue());
//        }
//        if (batteryWidget.isAdded()) {
//            batteryWidget.updateParameters(
//                    parser.getRange(),
//                    parser.getFirstTempSensorValue(),
//                    parser.getBatteryCapacity());
//        }
//    }
//
//    public static Boolean getConnectionState() {
//        return connectionState;
//    }
//
//    public static String getInputData() {
//        return inputData;
//    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static DataParser getParser() {
        return parser;
    }

    public static Message getMessage() {
        return message;
    }

    public static SettingsWidget getSettingsWidget () {
        return settingsWidget;
    }

}