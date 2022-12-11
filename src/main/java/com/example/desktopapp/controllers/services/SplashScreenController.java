package com.example.desktopapp.controllers.services;

import com.example.desktopapp.controllers.DashboardController;
import com.example.desktopapp.entity.Users;
import com.example.desktopapp.helpers.CommonClass;
import com.example.desktopapp.helpers.PaymentChecker;
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

    private PaymentChecker paymentChecker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            paymentChecker.checkerOutDated.setOnSucceeded(e -> {
                try {
                    FXMLLoader loader = openNormalWindow("/com/example/desktopapp/views/dashboard.fxml");
                    Scene scene = new Scene(loader.load());
                    DashboardController controller = loader.getController();
                    controller.setPaymentChecker(paymentChecker);
                    Stage dashboardStage = new Stage();
                    dashboardStage.setScene(scene);
                    dashboardStage.initStyle(StageStyle.UNDECORATED);

                    dashboardStage.show();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        });
    }


    public void setActiveUsers(Users activeUsers) {
        try {
            paymentChecker = new PaymentChecker(activeUsers);

            Thread thread = new Thread(paymentChecker.checkerOutDated);
            thread.setDaemon(true);
            thread.start();

            progress.progressProperty().bind(paymentChecker.checkerOutDated.progressProperty());

            if (activeUsers.getImage() != null) {
                userProfile.setFill(new ImagePattern(new Image(new FileInputStream(activeUsers.getImage()))));
            }
            userName.setText("Welcome " + activeUsers.getUsername());
            //waiting.textProperty().bind(checker.messageProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
