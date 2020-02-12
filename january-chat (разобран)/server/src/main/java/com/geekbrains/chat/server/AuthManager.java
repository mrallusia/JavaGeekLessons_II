package com.geekbrains.chat.server;

public interface AuthManager {
    String getNicknameByLoginAndPassword (String login, String password);
    boolean changeNickname(String client, String newName);
    void start();
    void stop();

}
