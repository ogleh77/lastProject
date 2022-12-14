package com.example.desktopapp.controllers;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import com.example.desktopapp.controllers.info.NotificationsController;
import com.example.desktopapp.controllers.info.OutDatedController;
import com.example.desktopapp.controllers.info.OutDatedCustomerInfoController;
import com.example.desktopapp.entity.Users;
import com.example.desktopapp.helpers.CommonClass;
import com.example.desktopapp.helpers.PaymentChecker;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private ImageView loadingImage;

    @FXML
    private HBox menuHbox;

    @FXML
    private Label outdatedCounter;

    @FXML
    private HBox outdatedHBox;

    @FXML
    private VBox sidePane;
    @FXML
    private HBox topProfile;
    private boolean visible;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        borderPane.setLeft(null);
        Platform.runLater(() -> {
            System.out.println(paymentChecker.getOutDatedCustomers().size() + " Out dated");
        });
    }

    @FXML
    void homeHandler(ActionEvent event) throws IOException {
        if (paymentChecker.getAllCustomers().size() == 0) {

            Stage loadingStage = loadingStage("/com/example/desktopapp/views/services/loading-window.fxml");

            Thread thread = new Thread(paymentChecker.fetchAllCustomers);
            thread.setDaemon(true);
            thread.start();
            paymentChecker.fetchAllCustomers.setOnSucceeded(e -> {
                FXMLLoader loader = null;
                try {
                    loader = openWindow("/com/example/desktopapp/views/home.fxml", borderPane, sidePane, menuHbox, topProfile);
                    HomeController controller = loader.getController();
                    controller.setBorderPane(borderPane);
                    controller.setPaymentChecker(paymentChecker);
                    loadingStage.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        } else {

            try {
                FXMLLoader loader = openWindow("/com/example/desktopapp/views/home.fxml", borderPane, sidePane, menuHbox, topProfile);
                HomeController controller = loader.getController();
                controller.setBorderPane(borderPane);
                controller.setPaymentChecker(paymentChecker);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
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

    @FXML
    void outDatedHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/info/outdated-info.fxml", borderPane, sidePane, menuHbox, topProfile);
        OutDatedCustomerInfoController controller = loader.getController();
        controller.setBorderPane(borderPane);
        controller.setPaymentChecker(paymentChecker);
    }

    @FXML
    void notificationHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/info/notifications.fxml", borderPane, sidePane, menuHbox, topProfile);
        NotificationsController controller = loader.getController();
        controller.setPaymentChecker(paymentChecker);
        controller.setBorderPane(borderPane);
    }

    @FXML
    void notificationMouseHandler(MouseEvent event) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/info/notifications.fxml", borderPane, sidePane, menuHbox, topProfile);
        NotificationsController controller = loader.getController();
        controller.setPaymentChecker(paymentChecker);
        controller.setBorderPane(borderPane);
    }

    @FXML
    void registrationHandler(ActionEvent event) throws IOException {

        FXMLLoader loader = openWindow("/com/example/desktopapp/views/registrations.fxml", borderPane, sidePane, menuHbox, topProfile);
        RegistrationController controller = loader.getController();
        controller.setPaymentChecker(paymentChecker);
        controller.setBorderPane(borderPane);
    }

    @FXML
    void singlePaymentHandler(ActionEvent event) {

    }

    @Override
    public void setPaymentChecker(PaymentChecker paymentChecker) {
        super.paymentChecker = paymentChecker;
        System.out.println("Dashboard paymentCheker " + paymentChecker);
        Users activeUser = paymentChecker.getActiveUser();
        try {
            if (activeUser.getImage() != null) {
                activeImage.setFill(new ImagePattern(new Image(new FileInputStream(activeUser.getImage()))));
            }
        } catch (FileNotFoundException e) {

        }
        activeUsername.setText(activeUser.getUsername());
    }


}
