package com.geekbrains.chat.server;

public interface AuthManager {
    String getNicknameByLoginAndPassword (String login, String password);

    void changeNickname(String client, String newName);
}
