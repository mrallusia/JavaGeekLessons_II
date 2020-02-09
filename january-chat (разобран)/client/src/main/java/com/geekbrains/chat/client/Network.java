package com.geekbrains.chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Network(int port) throws IOException {
        socket = new Socket("localhost", port);
        in = new DataInputStream(socket.getInputStream()); // создадим обработчики потоков
        out = new DataOutputStream(socket.getOutputStream()); // создадим обработчики потоков
    }

    public boolean isConnected() {
        if (socket == null || socket.isClosed()) {
            return false;
        }
        return true;
    }


    public void sendMsg(String msg) throws IOException {
        out.writeUTF(msg);
    }

    public String readMsg() throws IOException {
        return in.readUTF();
    }

    public void close() { // метод для закрытия работы с сетью
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
