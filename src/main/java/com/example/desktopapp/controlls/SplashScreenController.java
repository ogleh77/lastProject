package com.example.desktopapp.controlls;

import com.example.desktopapp.Common;
import com.example.desktopapp.entities.services.Users;
import com.example.desktopapp.models.PaymentChecker;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController extends Common implements Initializable {

    @FXML
    private ProgressBar progress;

    @FXML
    private AnchorPane splashPane;

    @FXML
    private Label waiting;

    private Users activeUser;

    private PaymentChecker checker;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Platform.runLater(() -> {
            checker.setOnSucceeded(e -> {
                try {
                    FXMLLoader loader = openNormalWindow("/com/example/desktopapp/views/dashboard.fxml");
                    Scene scene = new Scene(loader.load());
                    DashboardController controller = loader.getController();

                    controller.setPaymentChecker(checker);

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

    public void setActiveUser(Users activeUser) {
        try {
            this.activeUser = activeUser;
            checker = new PaymentChecker(activeUser);


            Thread thread = new Thread(checker);
            thread.setDaemon(true);
            thread.start();

            progress.progressProperty().bind(checker.progressProperty());

            waiting.textProperty().bind(checker.messageProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
