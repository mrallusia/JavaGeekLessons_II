package com.geekbrains.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainApp {
    // Домашнее задание:
    // 1. Разобраться с кодом. Задать вопросы что непонятно;


    public static void main(String[] args) {
        new Server(8189);
    }
}
