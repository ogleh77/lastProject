package com.example.desktopapp.controllers;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import com.example.desktopapp.controllers.service.NotificationController;
import com.example.desktopapp.services.CommonClass;
import com.example.desktopapp.services.PaymentChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController extends CommonClass implements Initializable {
    @FXML
    private Circle activeImage;

    @FXML
    private Label activeUsername;

    @FXML
    private BorderPane borderPane;

    @FXML
    private HBox menuHBox;

    @FXML
    private Label outdatedCounter;

    @FXML
    private HBox outdatedHBox;

    @FXML
    private VBox sidePane;

    @FXML
    private HBox topProfile;

    private PaymentChecker paymentChecker;

    private boolean visible = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        borderPane.setLeft(null);
    }


    @FXML
    void homeHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/home.fxml", borderPane, sidePane, menuHBox, topProfile);
        HomeController controller = loader.getController();
        controller.setBorderPane(borderPane);
        controller.setPaymentChecker(paymentChecker);
    }


    @FXML
    void notificationHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/service/notifications.fxml", borderPane, sidePane, menuHBox, topProfile);
        NotificationController controller = loader.getController();
        controller.setPaymentChecker(paymentChecker);
    }

    @FXML
    void notificationMouseHandler(MouseEvent event) {

    }

    @FXML
    void registrationHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/registrations.fxml", borderPane, sidePane, menuHBox, topProfile);
        RegistrationController controller = loader.getController();
        controller.setPaymentChecker(paymentChecker);
        controller.setBorderPane(borderPane);
    }

    @FXML
    void singlePaymentHandler(ActionEvent event) {

    }

    @FXML
    void menuClicked(MouseEvent event) {
        visible = !visible;

        if (visible) {
            SlideOutLeft slideOutLeft = new SlideOutLeft();
            slideOutLeft.setNode(sidePane);
            slideOutLeft.play();

            slideOutLeft.setOnFinished(e -> {
                borderPane.setLeft(null);

            });

        } else {
            new SlideInLeft(sidePane).play();
            borderPane.setLeft(sidePane);
        }
    }

    public void setPaymentChecker(PaymentChecker paymentChecker) {
        this.paymentChecker = paymentChecker;
    }

}
