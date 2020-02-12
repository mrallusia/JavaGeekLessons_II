package com.geekbrains.chat.server;

import java.sql.*;

public class DbAuthManager implements AuthManager {
    private Connection connection;
    private Statement stmt;
    private PreparedStatement psGetNicknameByLoginAndPasswors;
    private PreparedStatement psChangeNickname;
    private PreparedStatement psGetUserByNickname;



    @Override
    public void start() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:chatDataBase.db");
            stmt = connection.createStatement();
            chechUserTable();
            psGetNicknameByLoginAndPasswors = connection.prepareStatement("SELECT nickname FROM users WHERE login = ? AND pass = ?;");
            psChangeNickname = connection.prepareStatement("UPDATE users SET nickname = ? WHERE nickname = ?;");
            psGetUserByNickname = connection.prepareStatement("SELECT * FROM users WHERE nickname = ?;");
        } catch (ClassNotFoundException | SQLException e) {
            throw new AuthServiceException("Unable to connect");
        }
    }

    public void chechUserTable () throws SQLException {
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, login TEXT, pass TEXT, nickname TEXT);");
    }

    @Override
    public void stop() {
        if (psChangeNickname != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (psGetUserByNickname != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (psGetNicknameByLoginAndPasswors != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        try {
            psGetNicknameByLoginAndPasswors.setString(1, login);
            psGetNicknameByLoginAndPasswors.setString(2, password);
            try (ResultSet rs = psGetNicknameByLoginAndPasswors.executeQuery()){
                if (!rs.next()) {
                    return null;
                }
                return rs.getString(1);
            }
        } catch (SQLException e) {
            throw  new AuthServiceException("Unable to get nick by login/password");
        }
    }

    @Override
    public boolean changeNickname(String client, String newName) {
        try {
            if (isNicknameExist(newName)) {
                return false;
            }
            psChangeNickname.setString(1, newName);
            psChangeNickname.setString(2, client);
            psChangeNickname.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw  new AuthServiceException("Unable to change nickname");
        }
    }

    public boolean isNicknameExist(String client) {
        try {
            psGetUserByNickname.setString(1,client);
            try (ResultSet rs = psGetUserByNickname.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            throw  new AuthServiceException("Unable to change nickname");
        }
    }

}
