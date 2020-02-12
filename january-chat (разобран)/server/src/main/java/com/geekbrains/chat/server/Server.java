package com.geekbrains.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private AuthManager authManager;
    private List<ClientHandler> clients;


    public AuthManager getAuthManager() {
        return authManager;
    }

    public Server (int port) {
        clients = new ArrayList<>();
        authManager = new DbAuthManager();
        authManager.start();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен. Ожидаем подключения клиентов...");
            while (true) {
                Socket socket = serverSocket.accept(); //Блокирующая операция
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            authManager.stop();
        }
    }

    public void broadcastMsg (String msg) {
        for (ClientHandler o: clients) {
            o.sendMsg(msg);
        }
    }

    public void broadcastClientsList () {
        StringBuilder stringBuilder = new StringBuilder("/clients_list ");
        for (ClientHandler o: clients) {
            stringBuilder.append(o.getNickname()).append(" ");
        }
        stringBuilder.setLength(stringBuilder.length()-1);
        String out = stringBuilder.toString();
        broadcastMsg(out);
    }

    /**
     * Метод для приватных сообщений
     */

    public void privatetMsg (ClientHandler sender, String receiverNickname, String msg) {
        if (sender.getNickname().equals(receiverNickname)) {
            sender.sendMsg("Нельзя посылать личное сообщение самому себе");
            return;
        }
        for (ClientHandler o: clients){
            if (o.getNickname().equals(receiverNickname)) {
                o.sendMsg("from " + sender.getNickname() + ": " + msg);
                sender.sendMsg("to " + receiverNickname + ": " + msg);
                return;
            }
        }
        sender.sendMsg(receiverNickname + " не в сети");
    }

    public boolean isNickBusy (String nickname) {
        for (ClientHandler o: clients) {
            if (o.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void subscribe (ClientHandler clientHandler) {
        broadcastMsg(clientHandler.getNickname() + " зашёл в чат");
        clients.add(clientHandler);
        broadcastClientsList();
    }

    public synchronized void unsubscribe (ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastMsg(clientHandler.getNickname() + " вышел в чата");
        broadcastClientsList();
    }

}
