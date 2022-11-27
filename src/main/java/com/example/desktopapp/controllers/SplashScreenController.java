package com.example.desktopapp.controllers;


import com.example.desktopapp.entity.services.Users;
import com.example.desktopapp.services.PaymentChecker;
import com.example.desktopapp.services.CommonClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController extends CommonClass implements Initializable {
    @FXML
    private ProgressBar progress;

    @FXML
    private Label userName;

    @FXML
    private Circle userProfile;

    private Users activeUser;

    private PaymentChecker checker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        Platform.runLater(() -> {
//            checker.FetchCustomersByGander.setOnSucceeded(e -> {
//                try {
//                    FXMLLoader loader = openNormalWindow("/com/example/graadproject/views/views/dashboard.fxml");
//                    Scene scene = new Scene(loader.load());
//                    DashboardController controller = loader.getController();
//                    //   controller.setPaymentChecker(checker);
//                    Stage dashboardStage = new Stage();
//                    dashboardStage.setScene(scene);
//                    dashboardStage.initStyle(StageStyle.UNDECORATED);
//
//                    dashboardStage.show();
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//            });
//        });
    }

    public void setActiveUser(Users activeUser) {
//        try {
//            this.activeUser = activeUser;
//            checker = new PaymentChecker(activeUser);
//
//            Thread thread = new Thread(checker.FetchCustomersByGander);
//            thread.setDaemon(true);
//            thread.start();
//
//            progress.progressProperty().bind(checker.FetchCustomersByGander.progressProperty());
//
//            if (activeUser.getImage() != null) {
//                userProfile.setFill(new ImagePattern(new Image(new FileInputStream(activeUser.getImage()))));
//            }
//            userName.setText("Welcome " + activeUser.getUsername());
//            //waiting.textProperty().bind(checker.messageProperty());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
    }
}