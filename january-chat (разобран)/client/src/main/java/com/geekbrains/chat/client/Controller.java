package com.geekbrains.chat.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextArea textArea;

    @FXML
    TextField msgField, loginField;

    @FXML
    PasswordField passField;

    @FXML
    HBox loginBox;

    @FXML
    ListView<String> clientsList;

    private Network network;
    private boolean authenticated;
    private String nickname;

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
        loginBox.setVisible(!authenticated);
        loginBox.setManaged(!authenticated);
        msgField.setVisible(authenticated);
        msgField.setManaged(authenticated);
        clientsList.setVisible(authenticated);
        clientsList.setManaged(authenticated);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAuthenticated(false);
        clientsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==2){
                    msgField.setText("/w " + clientsList.getSelectionModel().getSelectedItem() + " ");
                    msgField.requestFocus();
                    msgField.selectEnd();
                }
            }
        });
    }

    public void tryToConnect(){
        try {
            if (network != null && network.isConnected()){
                return;
            }
            setAuthenticated(false);
            network = new Network(8189);
            Thread t = new Thread(() -> {
                try {
                    while (true) {
                        String msg = network.readMsg();
                        if (msg.startsWith("/authOk ")) {
                            nickname = msg.split( "\\s")[1];
                            setAuthenticated(true);
                            break;
                        }
                        textArea.appendText(msg + "\n");
                    }
                    while (true) {
                        String msg = network.readMsg();
                        if (msg.startsWith("/")) {
                            if (msg.equals("/end_confirm")) {
                                textArea.appendText("Завершено общение с сервером" + "\n");
                                break;
                            }
                            if (msg.startsWith("/clients_list")) {
                                Platform.runLater(() -> {
                                    clientsList.getItems().clear();
                                    String[] tokens = msg.split(" ");
                                    for (int i = 1; i < tokens.length; i++) {
                                        if (!nickname.equals(tokens[i])) {
                                            clientsList.getItems().add(tokens[i]);
                                        }
                                    }
                                });
                            }
                        } else {
                            textArea.appendText(msg + "\n");
                        }
                    }
                } catch (IOException e) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Соединение с серверов разорвано", ButtonType.OK);
                        alert.showAndWait();
                    });
                } finally {
                    network.close();
                    Platform.exit();
                }
            });
            t.setDaemon(true);
            t.start();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Невозможно подключиться к серверу", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void sendMsg(ActionEvent actionEvent) {
        try {
            network.sendMsg(msgField.getText());
            msgField.clear();
            msgField.requestFocus();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не удалось отправить сообщение, проверьте сетевое подключение", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void tryToAuth(ActionEvent actionEvent) {
        try {
            tryToConnect();
            network.sendMsg("/auth " + loginField.getText() + " " + passField.getText());
            loginField.clear();
            passField.clear();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не удалось отправить сообщение, проверьте сетевое подключение", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
