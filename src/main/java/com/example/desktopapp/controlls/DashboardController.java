package com.example.desktopapp.controlls;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import com.example.desktopapp.Common;
import com.example.desktopapp.controlls.service.NotificationsControllers;
import com.example.desktopapp.models.PaymentChecker;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
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
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController extends Common implements Initializable {
    @FXML
    private BorderPane borderPane;
    private TranslateTransition tt;
    @FXML
    private VBox sidePane;
    @FXML
    private Circle activeImage;

    @FXML
    private HBox menuHbox;

    @FXML
    private HBox topProfile;
    @FXML
    private HBox outdatedHBox;
    @FXML
    private Label outdatedCounter;
    @FXML
    private Label activeUsername;

    private boolean visible = true;
    private PaymentChecker paymentChecker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            borderPane.setLeft(null);


        });
    }

    @FXML
    void homeHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/home.fxml", borderPane, sidePane, menuHbox, topProfile);
        HomeController controller = loader.getController();
        controller.setPaymentChecker(paymentChecker);
    }

    @FXML
    void registrationHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/registrations.fxml", borderPane, sidePane, menuHbox, topProfile);
        RegistrationController controller = loader.getController();
        controller.setActiveUser(paymentChecker.getActiveUser());
        controller.setBorderPane(borderPane);
    }

    @FXML
    void notificationHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/services/notifications.fxml", borderPane, sidePane, menuHbox, topProfile);
        NotificationsControllers controllers = loader.getController();
        controllers.setPaymentChecker(paymentChecker);
        controllers.setBorderPane(borderPane);
    }
    @FXML
    void notificationMouseHandler(MouseEvent event) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/services/notifications.fxml", borderPane, sidePane, menuHbox, topProfile);
        NotificationsControllers controllers = loader.getController();
        controllers.setPaymentChecker(paymentChecker);
        controllers.setBorderPane(borderPane);
    }

    @FXML
    void menuClicked(MouseEvent event) {

        visible = !visible;

        if (visible) {
            SlideOutLeft slideOutLeft = new SlideOutLeft();
            slideOutLeft.setNode(sidePane);
            slideOutLeft.play();

            slideOutLeft.setOnFinished(e -> {
                tt = new TranslateTransition(Duration.millis(1000), borderPane.getCenter());
                tt.setByX(30f);
                tt.play();
                borderPane.setLeft(null);
//                borderPane.getCenter().setStyle("-fx-pref-width: 1010");
            });

        } else {
            new SlideInLeft(sidePane).play();
            tt = new TranslateTransition(Duration.millis(1000), borderPane.getCenter());
            tt.setByX(-30f);
            tt.play();
            borderPane.setLeft(sidePane);
        }

    }

    @Override
    public void setPaymentChecker(PaymentChecker paymentChecker) {
        this.paymentChecker = paymentChecker;
        //U bedel subax wcan insha Allah


        activeUsername.setText("Welcome :-  " + paymentChecker.getActiveUser().getUsername());

        if (paymentChecker.getOutdatedCustomers().size() > 0) {
            FadeIn fadeIn = new FadeIn(outdatedHBox);
            fadeIn.setCycleCount(10);
            fadeIn.play();
            if (paymentChecker.getOutdatedCustomers().size() > 9) {
                outdatedCounter.setText("9+");
            } else {
                outdatedCounter.setText(paymentChecker.getOutdatedCustomers().size() + "");
            }
        } else {
            outdatedCounter.setText("");
        }
    }
}
