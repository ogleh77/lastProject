package com.example.desktopapp.controllers;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import com.example.desktopapp.controllers.services.UserController;
import com.example.desktopapp.entities.services.Users;
import com.example.desktopapp.services.CommonClass;
import com.example.desktopapp.services.PaymentChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private HBox menuHbox;

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

        FXMLLoader loader = openWindow("/com/example/desktopapp/views/home.fxml", borderPane, sidePane, menuHbox, topProfile);
        HomeController controller = loader.getController();

        controller.setPaymentChecker(paymentChecker);
//          controller.setPaymentChecker(paymentChecker);
//         controller.setBorderPane(borderPane);
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
    void notificationHandler(ActionEvent event) {

    }

    @FXML
    void notificationMouseHandler(MouseEvent event) {

    }

    @FXML
    void registrationHandler(ActionEvent event) throws IOException {

        FXMLLoader loader = openWindow("/com/example/desktopapp/views/registrations.fxml", borderPane, sidePane, menuHbox, topProfile);
        RegistrationController controller = loader.getController();
        // controller.setActiveUser(paymentChecker.getActiveUser());
        controller.setPaymentChecker(paymentChecker);
        controller.setBorderPane(borderPane);

    }

    @FXML
    void singlePaymentHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = openNormalWindow("/com/example/desktopapp/views/service/user-creation.fxml");
        Scene scene = new Scene(loader.load());
//      UserController controller = loader.getController();
////    controller.setPaymentChecker(checker);
        Stage userStage = new Stage();
        userStage.setScene(scene);
        userStage.initStyle(StageStyle.UNDECORATED);

        userStage.show();
    }

    @Override
    public void setPaymentChecker(PaymentChecker paymentChecker) {
        this.paymentChecker = paymentChecker;
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
