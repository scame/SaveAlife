package com.krestone.savealife.data.tcp;


import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient {

    private String ipNumber;
    private int port;

    private BufferedReader input;
    private PrintWriter output;

    private boolean isRunning;

    private TcpClient(String ipNumber, int port) {
        this.ipNumber = ipNumber;
        this.port = port;
    }

    public void sendMessage(String message) {
        if (output != null && !output.checkError()) {
            output.println(message);
            output.flush();
        }
    }

    public void stopClient() {
        this.isRunning = false;
    }

    public void run() {
        this.isRunning = true;

        try {
            Socket socket = createSocket();
            initIoStreams(socket);
            startListening();
        } catch (IOException e) {
            Log.i("onxTcpException", e.getLocalizedMessage());
        }
    }

    private void startListening() throws IOException {
        String incomingMessage = null;
        while (isRunning) {
            if ((incomingMessage = input.readLine()) != null) {
                // TODO: handle message here
            }
        }
    }

    private Socket createSocket() throws IOException {
        InetAddress serverAddress = InetAddress.getByName(ipNumber);
        return new Socket(serverAddress, port);
    }

    private void initIoStreams(Socket socket) {
        try {
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            Log.i("onxStreamsException", e.getLocalizedMessage());
        }
    }
}
