package com.example.desktopapp;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TesterHandler implements Initializable {

    @FXML
    private Label doneLabel;

    @FXML
    private HBox loadingPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        task.setOnSucceeded(e -> {
            doneLabel.setVisible(true);
            loadingPane.setVisible(false);
        });
    }

    @FXML
    void startHandler(ActionEvent event) {

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }


    Task<Void> task = new Task<Void>() {
        @Override
        protected Void call() throws Exception {

            for (int i = 0; i < 100; i++) {

                System.out.println("Hi " + i);
                Thread.sleep(100);
            }
            return null;
        }
    };
}
