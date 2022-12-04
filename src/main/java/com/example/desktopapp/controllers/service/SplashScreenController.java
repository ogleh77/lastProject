package com.example.desktopapp.controllers.service;


import com.example.desktopapp.controllers.DashboardController;
import com.example.desktopapp.entity.services.Users;
import com.example.desktopapp.services.PaymentChecker;
import com.example.desktopapp.services.CommonClass;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController extends CommonClass implements Initializable {
    @FXML
    private ProgressBar progress;
    @FXML
    private Label userName;
    @FXML
    private Circle userProfile;
    private PaymentChecker checker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            checker.FetchCustomersByGander.setOnSucceeded(e -> {

                System.out.println(checker.getAllCustomers());
                FXMLLoader loader = null;
                try {
                    loader = openNormalWindow("/com/example/desktopapp/views/dashboard.fxml");
                    Scene scene = new Scene(loader.load());
                    DashboardController controller = loader.getController();
                    controller.setPaymentChecker(checker);
                    Stage dashboardStage = new Stage();
                    dashboardStage.setScene(scene);
                    dashboardStage.initStyle(StageStyle.UNDECORATED);

                    dashboardStage.show();

                    System.out.println("Outdated " + checker.getOutdatedCustomers().size());
                    System.out.println("===============================");
                    System.out.println("Active " + checker.getActiveCustomers().size());


                    System.out.println("All " + checker.getAllCustomers().size());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


            });
        });
    }

    public void setActiveUser(Users activeUser) {
        try {

            checker = new PaymentChecker(activeUser);

            Thread thread = new Thread(checker.FetchCustomersByGander);
            thread.setDaemon(true);
            thread.start();

            progress.progressProperty().bind(checker.FetchCustomersByGander.progressProperty());

            if (activeUser.getImage() != null) {
                userProfile.setFill(new ImagePattern(new Image(new FileInputStream(activeUser.getImage()))));
            }
            userName.setText("Welcome " + activeUser.getUsername());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
