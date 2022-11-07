package com.example.desktopapp.controlls.service;

import com.example.desktopapp.Common;
import com.example.desktopapp.entities.Customers;
import com.example.desktopapp.models.PaymentChecker;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NotificationsControllers extends Common implements Initializable {

    @FXML
    private VBox notiVBox;

    private PaymentChecker checker;

    private BorderPane borderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        OutDateTask.setOnSucceeded(e -> {

            notiVBox.setVisible(true);
            System.out.println("Ended≥.");
        });

    }


    @Override
    public void setPaymentChecker(PaymentChecker paymentChecker) throws IOException {
        this.checker = paymentChecker;
        Thread thread = new Thread(OutDateTask);
        thread.setDaemon(true);
        thread.start();
    }


    Task<Void> OutDateTask = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            for (Customers customer : checker.getOutdatedCustomers()) {
                System.out.println("Out dated is " + checker.getOutdatedCustomers().size());
                Platform.runLater(() -> {
                    FXMLLoader loader = null;
                    try {
                        loader = openNormalWindow("/com/example/desktopapp/views/services/outdated.fxml");
                        AnchorPane anchorPane = loader.load();
                        OutDatedController controller = loader.getController();
                        controller.setCustomer(customer);
                        controller.setBorderPane(borderPane);
                        notiVBox.getChildren().add(anchorPane);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });

            }
            return null;
        }
    };


    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }
}
