package com.example.desktopapp.controllers;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import com.example.desktopapp.genrals.Commons;
import com.example.desktopapp.models.CustomerDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class DashboardController extends Commons implements Initializable {
    @FXML
    private BorderPane borderPane;


    @FXML
    private HBox menuHbox;

    @FXML
    private VBox sidePane;

    @FXML
    private HBox topProfile;

    private boolean visible;
    private double startX;
    private double startY;
    CustomerDAO customerDAO = new CustomerDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    void RegistrationHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/reg.fxml", borderPane, sidePane, menuHbox, topProfile);

    }

    @FXML
    void homeHandler(ActionEvent event) {

    }

    @FXML
    void notificationHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/notifications.fxml", borderPane, sidePane, menuHbox, topProfile);
    }

    @FXML
    void paymentHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/payments.fxml", borderPane, sidePane, menuHbox, topProfile);

    }

    @FXML
    void singlePaymentHandler(ActionEvent event) throws SQLException {
        System.out.println(customerDAO.fetchAll().get(0));


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
                borderPane.getCenter().setStyle("-fx-pref-width: 1010");
            });

        } else {
            new SlideInLeft(sidePane).play();
            borderPane.getCenter().setStyle(null);
            borderPane.setLeft(sidePane);
        }
    }

    @FXML
    void maximizeHandler(MouseEvent event) {
        Stage primaryStage = (Stage) borderPane.getScene().getWindow();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

    }
}
