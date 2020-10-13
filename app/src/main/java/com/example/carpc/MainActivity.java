package com.example.carpc;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.carpc.instruments.DataParser;
import com.example.carpc.settings.SettingsWidget;
import com.example.carpc.settings.tabs.TerminalTab;
import com.example.carpc.widgets.BatteryManagerWidget;
import com.example.carpc.widgets.BatteryWidget;
import com.example.carpc.widgets.IconStatusLeftWidget;
import com.example.carpc.widgets.IconStatusRightWidget;
import com.example.carpc.widgets.SpeedometerWidget;
import com.example.carpc.widgets.TripManagerWidget;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "myLogs";
    private static SpeedometerWidget speedometerWidget;
    private static BatteryWidget batteryWidget;
    private static BatteryManagerWidget batteryManagerWidget;
    private static TripManagerWidget tripManagerWidget;
    private static IconStatusRightWidget iconStatusRightWidget;
    private static IconStatusLeftWidget iconStatusLeftWidget;
    private static SettingsWidget settingsWidget;
    private static TerminalTab terminalTab;
    public static DataParser parser;
    public static Boolean connectionStateFlag = false;
    private Socket socket;
    private androidx.fragment.app.FragmentTransaction fTrans;
    static boolean flagAutoConnect, setParamsFlag = false;
    static volatile boolean subscribe = true, unsubscribe = false, connectionState = false, sendMessageFlag = false;
    static int port;
    static String inputData, address;
    public static String message;
    private static final String UNSUBSCRIBE = "@a0";
    private static final String SUBSCRIBE = "@a1";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initParams();

//        ConnectionTab connectionTab = new ConnectionTab();
//        address = connectionTab.getServerAddress();
//        port = connectionTab.getServerPort();
        address = "192.168.1.7";
        port = 8000;

        if (address != null && port > 0) {
            flagAutoConnect = true;
            startCommunication(address, port);

        } else {
            flagAutoConnect = false;
        }

        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fTrans.add(R.id.centerCont, speedometerWidget);
        fTrans.add(R.id.rightUpCont, iconStatusRightWidget);
        fTrans.add(R.id.leftUpCont, iconStatusLeftWidget);
        fTrans.add(R.id.bottomLeftCont, batteryWidget);
        fTrans.setCustomAnimations(R.animator.left_in, R.animator.right_in).add(R.id.leftCont, batteryManagerWidget);
        fTrans.setCustomAnimations(R.animator.left_out, R.animator.right_out).add(R.id.rightCont, tripManagerWidget);
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment f : fragmentList) {
            fTrans.detach(f);
        }
        fTrans.commit();
    }

    public void initParams() {
        parser = new DataParser();
        speedometerWidget = new SpeedometerWidget();
        batteryManagerWidget = new BatteryManagerWidget();
        tripManagerWidget = new TripManagerWidget();
        iconStatusRightWidget = new IconStatusRightWidget();
        iconStatusLeftWidget = new IconStatusLeftWidget();
        settingsWidget = new SettingsWidget();
        batteryWidget = new BatteryWidget();
        terminalTab = new TerminalTab();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        sendMessage(SUBSCRIBE);
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment f : fragmentList) {
            fTrans.attach(f);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeCommunication();
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

        for (Fragment f : fragmentList) {
            fTrans.detach(f);
        }
    }

    public void onClick(View v) {
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        switch (v.getId()) {
//            case R.id.btnCharge:
//                try {
//                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> btn Charge");
//                closeCommunication();
//            } catch (NullPointerException e) {
//
//            }
//                break;
            case R.id.btnDashboard:
                sendMessage(SUBSCRIBE);
                // readLineFlag = true;
                hideKeyboard(this);
                fTrans.addToBackStack(null);
                if (settingsWidget.isAdded()) {
                    fTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    fTrans.detach(settingsWidget);
                    fTrans.remove(settingsWidget);
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> settingsWidget is removed when btnDashboard onClick");
                }
                fTrans.attach(iconStatusRightWidget);
                fTrans.attach(iconStatusLeftWidget);
                fTrans.attach(speedometerWidget);
                fTrans.attach(batteryWidget);
                fTrans.setCustomAnimations(R.animator.left_in, R.animator.left_out).attach(batteryManagerWidget);
                fTrans.setCustomAnimations(R.animator.left_out, R.animator.left_in).attach(tripManagerWidget);
                break;
            case R.id.btnSettings:
                setParamsFlag = false;

                if (speedometerWidget.isVisible()) {
                    fTrans.detach(speedometerWidget);
                }
                if (batteryManagerWidget.isVisible()) {
                    fTrans.detach(batteryManagerWidget);
                }
                if (tripManagerWidget.isVisible()) {
                    fTrans.detach(tripManagerWidget);
                }
                if (iconStatusLeftWidget.isVisible()) {
                    fTrans.detach(iconStatusLeftWidget);
                }
                if (iconStatusRightWidget.isVisible()) {
                    fTrans.detach(iconStatusRightWidget);
                }
                if (batteryWidget.isVisible()) {
                    fTrans.detach(batteryWidget);
                }

                fTrans.addToBackStack(null);


                if (!settingsWidget.isVisible()) {
                    fTrans.add(R.id.frgmCont, settingsWidget);
                    fTrans.attach(settingsWidget);
                }
                sendMessage(UNSUBSCRIBE);
                break;
        }
        fTrans.commit();
    }

    public void startCommunication(final String address, final int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("try connect to: " + address + ":" + port);

                    socket = new Socket();
                    socket.connect(new InetSocketAddress(address, port), 500);
                    System.out.println("connected");
                    connectionState = true;
                    if (connectionState) {
                        final Scanner scanner = new Scanner(socket.getInputStream());
                        final PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

                        /*  input stream thread  */
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (connectionState) {
                                    try {
                                        socket.setSoTimeout(0);
                                        if (scanner.hasNextLine()) {
                                            inputData = scanner.nextLine();
                                        } else {
                                            setParamsFlag = false;
                                            throw new IOException();
                                        }
                                        if (!inputData.equals(null) && !inputData.equals("")) {
                                            setParamsFlag = true;
                                        }

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (setParamsFlag) setParameters(inputData);
                                                setParamsFlag = false;
                                            }
                                        });
                                    } catch (Exception e) {
                                        System.out.println("scanner no line " + e);
                                        connectionState = false;
                                        System.out.println("connectionState false -> interrupt input stream Thread");
                                        Thread.currentThread().interrupt();
                                    }
                                }
                            }
                        }).start();

                        /* output stream thread */
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (connectionState) {
                                    try {
                                        if (sendMessageFlag) {
                                            writer.println(message);
                                            sendMessageFlag = false;
                                        }
                                        Thread.sleep(2);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        connectionState = false;
                                        break;
                                    }
                                }
                                System.out.println("connectionState false -> interrupt output stream Thread");
                                if (!connectionState) Thread.currentThread().interrupt();
                            }
                        }).start();
                    }
                    while (true) {
                        if (!connectionState) {
                            //socket.setSoTimeout(100);
                            System.out.println("connectionState false -> throw new Exception");
                            throw new Exception();
                        }
                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                    try {
                        System.out.println("connection lost");
                        socket.close();
                        inputData = null;
                        setParamsFlag = false;
                        connectionState = false;
                        //Thread.sleep(1000);
                        Thread.currentThread().interrupt();
                        if (flagAutoConnect) {
                            System.out.println("reconnecting");
                            startCommunication(address, port);
                        }
                    } catch (IOException ex) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void closeCommunication() {
        try {
            flagAutoConnect = false;
            socket.close();
            connectionState = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startCommunicationWithNewParams(final String newAddress, final int newPort) {
        address = newAddress;
        port = newPort;
        flagAutoConnect = true;
        startCommunication(newAddress, newPort);
    }

    public static void setParameters(String inputData) throws NullPointerException {
        parser.parseInputData(inputData);

        if (settingsWidget.isVisible() && TerminalTab.updateFlag) {
            terminalTab.showNewMessage(inputData);
        }
        if (batteryManagerWidget.isAdded()) {
            batteryManagerWidget.updateParameters(
                    parser.getVoltage(),
                    parser.getCurrent(),
                    parser.getBatteryCapacity(),
                    parser.getMinCellVoltage(),
                    parser.getMaxCellVoltage());
        }
        if (speedometerWidget.isAdded()) {
            speedometerWidget.updateSpeed(
                    parser.getSpeed());
        }
        if (tripManagerWidget.isAdded()) {
            tripManagerWidget.updateParameters(
                    parser.getLastChargePassedDistance(),
                    parser.getRange(),
                    parser.getTotalDistance());
        }
        if (iconStatusRightWidget.isAdded() && parser.analogInputValuesChanged()) {
            iconStatusRightWidget.updateParameters(
                    parser.getAnalogInputsValue());
        }
        if (batteryWidget.isAdded()) {
            batteryWidget.updateParameters(
                    parser.getRange(),
                    parser.getFirstTempSensorValue(),
                    parser.getBatteryCapacity());
        }
    }

    public static Boolean getConnectionState() {
        return connectionState;
    }

    public static String getInputData() {
        return inputData;
    }

    public void hideKeyboard(Activity activity) {
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

    public static void sendMessage(String messageToSend) {
        System.out.println("Message to send: " + messageToSend);
        message = messageToSend;
        sendMessageFlag = true;
    }

}