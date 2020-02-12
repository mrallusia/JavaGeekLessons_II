package com.geekbrains.chat.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BasicAuthManager implements AuthManager {
    private class Entry {
        private String login;
        private String password;
        private String nickname;


        public Entry (String login, String password, String nickname) {
            this.login = login;
            this.password = password;
            this.nickname = nickname;
        }
    }

    private List<Entry> users;

    private static Connection connection;
    private static Statement stmt;

    /**
     * Подключение к базе
     */

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:chatDataBase.db");
        stmt = connection.createStatement();
    }

    /**
     * отключение от базе
     */
    public static void disconect() {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Базу загружем в лист
     */
    public BasicAuthManager() {
        this.users = new ArrayList<>();
        try {
            connect();
            ResultSet rs = stmt.executeQuery("SELECT*FROM clients");
            while (rs.next()) {
                users.add(new Entry(rs.getString(3), rs.getString(4), rs.getString(2)));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            disconect();
        }

    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        for (Entry u: users) {
            if (u.login.equals(login) && u.password.equals(password)) {
                return u.nickname;
            }
        }
        return null;
    }

    /**
     * Меняем ник в базе и в листе
     */
    @Override
    public boolean changeNickname (String nickname, String newNickname){
        throw new UnsupportedOperationException();
    }

    @Override
    public void start() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException();
    }
}
