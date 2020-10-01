package com.example.carpc.instruments;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ClientSocket {
    //    public class ClientSocket implements Closeable {
    private Socket socket;
    //    private BufferedReader reader;
//    private BufferedWriter writer;
    private Scanner reader;
    PrintWriter writer;
    public volatile boolean connectionState;
    int timeout = 2 * 1000; //ms * 1000

    public ClientSocket(String ip, int port) {
        try {
            socket = new Socket();
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Connect to " + ip + ":" + port);
            socket.connect(new InetSocketAddress(ip, port), timeout);
            connectionState = true;
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Connected");
            reader = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream(), true);
//            reader = createReader();
//            writer = createWriter();
        } catch (IOException e) {
            e.printStackTrace();
            connectionState = false;
        }
    }

    public void writeLine(String message) {
        writer.println(message);
//        try {
//            writer.write(message);
//                       writer.flush();
//        } catch (IOException e) {
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ClientSocket.writeLine - IOException");
//            connectionState = false;
//        }
    }

    public String readLine() throws IOException {
//        String message = "";
//        if (reader.hasNextLine()) {
//            message = reader.nextLine();
//        }
//        String message = "";
//        message = reader.readLine();
        return  reader.nextLine();
    }

    public void setSoTimeout(int secondsTimeout) {
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

    public void close() throws IOException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ClientSocket.close()");
        connectionState = false;
        writer.close();
        reader.close();
        socket.close();
        Thread.currentThread().interrupt();
    }


}
