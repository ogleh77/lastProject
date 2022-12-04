package com.example.desktopapp.controllers.service;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.services.CommonClass;
import com.example.desktopapp.services.PaymentChecker;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NotificationController extends CommonClass implements Initializable {
    @FXML
    private VBox vboxOutdated;

    //    @FXML
//    private VBox vboxWarning;
    @FXML
    private Pagination pagination;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            if (paymentChecker.getAllCustomers().size() % 5 == 0) {
                pagination.setPageCount(pagination.getPageCount() + 1);
            }
//            pagination.setPageCount((paymentChecker.getAllCustomers().size() % 5 == 0) ?);
            pagination.setPageFactory(this::warningPage);


        });
    }

    @Override
    public void setPaymentChecker(PaymentChecker paymentChecker) {
        super.setPaymentChecker(paymentChecker);
    }

    private VBox warningPage(int index) {
        VBox vBox = new VBox();
        vBox.setPrefHeight(593);
        vBox.setPrefWidth(559);
        vBox.setSpacing(5);
        FXMLLoader loader;
        AnchorPane anchorPane;
        try {
            for (Customers customer : paymentChecker.getOutdatedCustomers()) {
                loader = openNotificationWindow("/com/example/desktopapp/views/service/outdated.fxml");
                anchorPane = loader.load();
                OutdatedController controller = loader.getController();
                controller.setCustomer(customer);
                vBox.getChildren().add(anchorPane);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return vBox;
    }
}
