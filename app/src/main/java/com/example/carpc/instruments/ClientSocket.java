package com.example.carpc.instruments;

import android.util.Log;

import com.example.carpc.MainActivity;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class ClientSocket implements Closeable {
    private Socket socket;
    private Scanner scanner;
    private PrintWriter printWriter;
    private boolean connectionState = false;
    private boolean reconnect = true;
    private final String TAG = "SOCKET";
    private static DataParser dataParser;
    private DataBase db;
    Message message = MainActivity.getMessage();

    public static String inputMessage;
    public static boolean newMessageFlag = false;

    public ClientSocket(final String address, final int port, final DataParser parser) {
        createConnection(address, port, parser, false);
    }

    public ClientSocket(final String address, final int port, final DataParser parser, boolean subscribe) {
        createConnection(address, port, parser, subscribe);
    }

    private void createConnection(final String address, final int port, final DataParser parser, final boolean subscribe) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket();
                    dataParser = parser;
                    db = new DataBase();
                    socket.connect(new InetSocketAddress(address, port), 500);
                    scanner = new Scanner(socket.getInputStream());
                    if (subscribe) {
                        sendMessage(db.SUBSCRIBE);
                    } else {
                        sendMessage(db.UNSUBSCRIBE);
                    }
                    readInputStream();
                    connectionState = true;
                } catch (IOException e) {
                    connectionState = false;
                    if (reconnect) createConnection(address, port, parser, subscribe);
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
                            newMessageFlag = true;
                            inputMessage = scanner.nextLine();
                            message.setMessage(inputMessage, true);
                            dataParser.parseInputData(inputMessage);
                        }
                    } catch (Exception e) {
                        connectionState = false;
                        Log.i(TAG, Objects.requireNonNull(e.getMessage()));
                        e.printStackTrace();
                    }
//                    Log.i(TAG, i + " > " + inputMessage);
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
                    Log.i(TAG, "OUT: " + message);
                    Thread.sleep(1);
                    if (!connectionState) Thread.currentThread().interrupt();
                } catch (Exception e) {
                    connectionState = false;
                    Log.i(TAG, Objects.requireNonNull(e.getMessage()));
                    e.printStackTrace();
                    Log.i(TAG, "> connection state: " + connectionState + "\n" +
                            "connectionState false -> interrupt output stream Thread");
                    if (!connectionState) Thread.currentThread().interrupt();
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
            sendMessage(db.UNSUBSCRIBE);
            close();
        } catch (IOException e) {
            Log.i(TAG, Objects.requireNonNull(e.getMessage()));
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
