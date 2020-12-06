package com.example.carpc.instruments;

import android.util.Log;

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
    private String inputMessage;
    private final String TAG = "SOCKET";
    private static DataParser dataParser;

    public ClientSocket(final String address, final int port, final DataParser parser) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("run ClientSocket constuctor");
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(address, port), 500);
                    scanner = new Scanner(socket.getInputStream());
                    dataParser = parser;
                    System.out.println("connected");
                    connectionState = true;
                    Log.i(TAG, "> connection state: " + connectionState);
                    readInputStream();
                    sendMessage("@a1");
                } catch (IOException e) {
                    connectionState = false;
                    Log.i(TAG, "> connection state: " + connectionState);
                    Log.i(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /*  input stream thread  */
    private void readInputStream() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (connectionState) {
                    try {
                        socket.setSoTimeout(0);
                        if (scanner.hasNextLine()) {
                            inputMessage = scanner.nextLine();
                            dataParser.parseInputData(inputMessage);
                            Log.i(TAG, inputMessage);
                        }
                    } catch (Exception e) {
                        connectionState = false;
                        Log.i(TAG, Objects.requireNonNull(e.getMessage()));
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

    public void disconnect() {
        try {
            reconnect = false;
            close();
        } catch (IOException e) {
            Log.i(TAG, Objects.requireNonNull(e.getMessage()));
            e.printStackTrace();
        }
    }

    public String readMessage() {
        return inputMessage;
    }

    public boolean getConnectionState() {
        return connectionState;
    }

    @Override
    public void close() throws IOException {
        if (socket != null) socket.close();
        if (printWriter != null) printWriter.close();
        connectionState = false;
    }
}
