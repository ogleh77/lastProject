package com.example.desktopapp.controllers;

import com.example.desktopapp.entity.Users;
import com.example.desktopapp.helpers.CommonClass;
import com.example.desktopapp.helpers.PaymentChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

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
    private StackPane stackPane;

    @FXML
    private HBox topProfile;
    @FXML
    private ProgressBar loadingProgress;
    @FXML
    private HBox loadingHBox;

    private PaymentChecker paymentChecker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        stackPane.getChildren().remove(1);
    }

    @FXML
    void homeHandler(ActionEvent event) throws IOException {
        if (paymentChecker.getAllCustomers().size() == 0) {
            stackPane.getChildren().add(1, loadingHBox);
            Thread thread = new Thread(paymentChecker.fetchAllCustomers);
            thread.setDaemon(true);
            thread.start();
            loadingProgress.progressProperty().bind(paymentChecker.fetchAllCustomers.progressProperty());
            paymentChecker.fetchAllCustomers.setOnSucceeded(e -> {
                FXMLLoader loader = null;
                try {
                    loader = openWindow("/com/example/desktopapp/views/home.fxml", borderPane, sidePane, menuHbox, topProfile);
                    HomeController controller = loader.getController();
                    controller.setBorderPane(borderPane);
                    // controller.setStackPane(stackPane);
                    //  controller.setProgressLoading(loadingProgress);
                    controller.setPaymentChecker(paymentChecker);
                    stackPane.getChildren().remove(1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            });
        } else {
            FXMLLoader loader = null;
            try {
                loader = openWindow("/com/example/desktopapp/views/home.fxml", borderPane, sidePane, menuHbox, topProfile);
                HomeController controller = loader.getController();
                controller.setBorderPane(borderPane);
                // controller.setStackPane(stackPane);
                //  controller.setProgressLoading(loadingProgress);
                controller.setPaymentChecker(paymentChecker);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @FXML
    void menuClicked(MouseEvent event) {

    }

    @FXML
    void notificationHandler(ActionEvent event) {

    }

    @FXML
    void notificationMouseHandler(MouseEvent event) {

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
        this.paymentChecker = paymentChecker;
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
