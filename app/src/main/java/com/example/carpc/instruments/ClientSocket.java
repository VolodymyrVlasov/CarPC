package com.example.carpc.instruments;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class ClientSocket implements Closeable {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    public boolean connectionState;
    int timeout = 10 * 1000; //ms * 1000

    public ClientSocket(String ip, int port) {
        try {
            socket = new Socket();
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Connect to " + ip + ":" + port);
            socket.connect(new InetSocketAddress(ip, port), timeout);
            connectionState = true;
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Connected");
            reader = createReader();
            writer = createWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLine(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ClientSocket.writeLine - IOException");
        }
    }

    public String readLine() {
        String message = null;
        try {
            message = reader.readLine();
        } catch (IOException e) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ClientSocket.readLine - IOException");
        }
        return message;
    }

    public void setSoTimeout(int secondsTimeout)  {
        try {
            this.socket.setSoTimeout(secondsTimeout * 1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public Boolean isOutputShutdown() {
        return socket.isOutputShutdown();
    }

    public Boolean isInputShutdown() {
        return socket.isInputShutdown();
    }

    private BufferedWriter createWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    private BufferedReader createReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public boolean getConnectionState() {
        return connectionState;
    }

    @Override
    public void close() throws IOException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ClientSocket.close()");
        connectionState = false;
        writer.close();
        reader.close();
        socket.close();
        Thread.currentThread().interrupt();
    }



}
