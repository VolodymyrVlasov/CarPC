package com.example.carpc.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.carpc.MainActivity;
import com.example.carpc.models.DataPrefs;
import com.example.carpc.models.Message;
import com.example.carpc.utils.AppConstants;
import com.example.carpc.utils.DataParser;
import com.example.carpc.widgets.settingsScreen.tabs.ConnectionTab;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TCPClient implements Closeable {
    private static TCPClient instance = null;

    private Socket socket;
    private Scanner scanner;
    private PrintWriter printWriter;
    private Message message = MainActivity.getMessage();
    private boolean connectionState = false;
    private boolean reconnect = true;
    private final String TAG = "SOCKET";
    private static String inputMessage;

    //    private TCPClientListener listener;
    private List<TCPClientListener> networkListeners;

    public interface TCPClientListener {
        public void OnDataReceive(DataParser data);
    }

    public void addNetworkListener(TCPClientListener newListener) {
        this.networkListeners.add(newListener);
    }

//    public void setTCPClientListener(TCPClientListener listener) {
//        this.listener = listener;
//    }

    public static TCPClient getInstance(Context ctx) {
        if (instance == null) {
            DataPrefs data = DataPrefs.getInstance(ctx);
            instance = new TCPClient(data.getIP(), data.getPort());
        }

        return instance;
    }

    private TCPClient(final String address, final int port) {
        createConnection(address, port, true);
        networkListeners = new ArrayList<>();
    }

    public String getLocalNetworkAddress(Context ctx) {
        WifiManager wifiMan = (WifiManager) ctx.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        int ipAddress = wifiInf.getIpAddress();
        @SuppressLint("DefaultLocale") String ip = String.format("%d.%d.%d.%d",
                (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));
        return ip;
    }

    public void createConnection(final String address, final int port, final boolean subscribe) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (connectionState == false) {
                        socket = new Socket();
                        socket.connect(new InetSocketAddress(address, port), 500);
                        scanner = new Scanner(socket.getInputStream());
                        readInputStream();
                        connectionState = true;
                        if (subscribe) {
                            sendMessage(AppConstants.SUBSCRIBE);
                        } else {
                            sendMessage(AppConstants.UNSUBSCRIBE);
                        }
                    }

                } catch (IOException e) {
                    connectionState = false;
                    if (reconnect) createConnection(address, port, subscribe);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void readInputStream() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (connectionState) {
                    try {

                        socket.setSoTimeout(0);
                        if (scanner.hasNextLine()) {
                            inputMessage = scanner.nextLine();
                            message.setMessage(inputMessage, true);

                            DataParser data = DataParser.getInstance().parseInputData(inputMessage);
                            if(networkListeners != null) {

                                for(TCPClientListener listener : networkListeners) {
                                    listener.OnDataReceive(data);
                                }

//                                listener.OnDataReceive(data);
                            }
                        }
                    } catch (Exception e) {
                        printWriter.println("transmit 0");
                        printWriter.println("@a0");
                        connectionState = false;

                        if (ConnectionTab.isVisible) {
                            ConnectionTab.setConnectionStateIndicator();
                        }
                        e.printStackTrace();
                    }
                }
                Log.i(TAG, "> connection state: " + connectionState + "\n" +
                        "connectionState false -> interrupt input stream Thread");
                if (!connectionState) Thread.currentThread().interrupt();
            }
        }).start();
    }

    /*  output stream thread  */
    public void sendMessage(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    printWriter = new PrintWriter(socket.getOutputStream(), true);
                    printWriter.println(message);
                    Thread.sleep(1);
                    if (!connectionState) Thread.currentThread().interrupt();
                } catch (Exception e) {
                    connectionState = false;
                    if (ConnectionTab.isVisible) {
                        ConnectionTab.setConnectionStateIndicator();
                    }
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    /*  input stream thread  */
    public String readMessage() {
        return inputMessage;
    }

    public boolean isConnected() {
        return connectionState;
    }

    public void setReconnect(boolean flag) {
        reconnect = flag;
    }

    public void disconnect() {
        try {
            reconnect = false;
            sendMessage(AppConstants.UNSUBSCRIBE);
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        if (socket != null) socket.close();
        if (printWriter != null) printWriter.close();
        connectionState = false;
    }
}
