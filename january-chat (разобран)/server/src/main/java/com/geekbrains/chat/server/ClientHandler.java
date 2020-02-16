package com.geekbrains.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler {


    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public ClientHandler(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());

        ExecutorService service = Executors.newFixedThreadPool(4);

        service.execute(() -> {
            try {

                while (true) {
                    String msg = in.readUTF();
                    if (msg.startsWith("/auth")) {
                        String[] token = msg.split("\\s", 3);
                        String nickFromAuthManager = server.getAuthManager().getNicknameByLoginAndPassword(token[1], token[2]);
                        if (nickFromAuthManager != null) {
                            if (server.isNickBusy(nickFromAuthManager)) {
                                sendMsg("Указанный пользователь уже в чате");
                                continue;
                            }
                            nickname = nickFromAuthManager;
                            sendMsg("/authOk " + nickname);
                            server.subscribe(this);

                            break;
                        } else {
                            sendMsg("Указан неверный логин/пароль");
                        }
                    }
                }

                while (true) {
                    String msg = in.readUTF();
                    System.out.print("Сообщение от клиента: " + msg + "\n");
                    if (msg.startsWith("/")) {
                        // private mesasge
                        if (msg.startsWith("/w")) {
                            String[] token = msg.split(" ", 3);
                            server.privatetMsg(this, token[1], token[2]);
                            continue;
                        }
                        // nick_change
                        if (msg.startsWith("/change_nickname ")) {
                            String[] token = msg.split(" ", 2);
                            String newNickname = token[1];
                            if(server.getAuthManager().changeNickname(nickname, newNickname)) {
                                sendMsg("/set_nick_to" + newNickname);
                            } else {
                                sendMsg("Сервер: не удалось сменить ник");
                            }
                            nickname = token[1];
                            server.broadcastClientsList();
                            continue;
                        }
                        if (msg.toLowerCase().equals("/end")) {
                            sendMsg("/end_confirm");
                            break;
                        }

                    } else {
                        server.broadcastMsg(nickname + ": " + msg);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
                service.shutdown();
            }
        });

    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        server.unsubscribe(this);
        nickname = null;
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
