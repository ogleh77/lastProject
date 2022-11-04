package com.example.desktopapp;

import animatefx.animation.FadeIn;
import animatefx.animation.Shake;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import com.example.desktopapp.models.PaymentChecker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public abstract class Common {

    public final ObservableList<String> shift;
    public final ObservableList<String> paidBy;
    private final Shake shake;
    private final SlideInRight slideInRight;
    private final SlideInLeft slideInLeft;
    private final FadeIn fadeIn;

    private PaymentChecker paymentChecker;

    public Common() {
        shift = FXCollections.observableArrayList();
        paidBy = FXCollections.observableArrayList();
        this.shift.addAll("Morning", "Noon", "Afternoon", "Night");
        this.paidBy.addAll("eDahab", "Zaad service", "other");
        this.shake = new Shake();
        this.slideInRight = new SlideInRight();
        this.slideInLeft = new SlideInLeft();
        this.fadeIn = new FadeIn();
    }


    protected FXMLLoader openWindow(String url, BorderPane borderPane, VBox sidePane, HBox menu, HBox profile) throws IOException {

        if (sidePane != null && !sidePane.isVisible()) {

            sidePane.setVisible(true);
            slideInLeft.setNode(sidePane);
            slideInLeft.play();
            borderPane.setLeft(sidePane);
        }
        //side menu

        if (menu != null) {
            menu.setVisible(true);
            fadeIn.setNode(menu);
            slideInLeft.playOnFinished(fadeIn);
            fadeIn.play();
        }


        //top profile
        if (profile != null) {
            profile.setVisible(true);
            fadeIn.setNode(profile);
            fadeIn.play();
        }
//
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));

        AnchorPane anchorPane = loader.load();
        slideInRight.setNode(anchorPane);
        slideInRight.play();
        borderPane.setCenter(anchorPane);

        return loader;
    }

    protected FXMLLoader openNormalWindow(String url) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(url));


        return fxmlLoader;
    }

    public Alert message(Alert.AlertType alertType, String content, String title) {
        Alert alert = new Alert(alertType);
        alert.setContentText(content);
        alert.setTitle(title);
        return alert;
    }

    public boolean checkDigitsAndNullable(TextField textField) {

        return (!textField.getText().matches("[0-9]*") && (!textField.getText().isBlank() && !textField.getText().isBlank()));

    }

    public boolean isValid(ObservableList<Control> mandotoryFileds, ToggleGroup group) {
        boolean isValid = true;

        try {

            for (Control control : mandotoryFileds) {
                if (control instanceof TextInputControl) {
                    if ((((TextInputControl) control).getText().isBlank() || ((TextInputControl) control).getText().isEmpty())) {
                        shake.setNode(control);
                        control.setStyle("-fx-border-color: #cb3d3d;-fx-border-width: 2");
                        shake.play();
                        isValid = false;
                    } else {
                        control.setStyle(null);
                    }
                } else if (control instanceof ComboBoxBase<?> && (((ComboBoxBase<?>) control).getValue() == null)) {
                    shake.setNode(control);
                    shake.play();
                    control.setStyle("-fx-border-color: #cb3d3d;-fx-border-width: 2");
                    isValid = false;
                } else {
                    control.setStyle(null);
                }
            }
            if (group.getSelectedToggle() == null) {
                shake.setNode((Node) group.getToggles().get(0));
                shake.play();
                shake.setNode((Node) group.getToggles().get(1));
                ((Node) group.getToggles().get(0)).setStyle("-fx-border-color: #cb3d3d;-fx-border-width: 2");
                ((Node) group.getToggles().get(1)).setStyle("-fx-border-color: #cb3d3d;-fx-border-width: 2");
                shake.play();

                isValid = false;
            } else {
                ((Node) group.getToggles().get(0)).setStyle(null);
                ((Node) group.getToggles().get(1)).setStyle(null);
            }


        } catch (NullPointerException e) {
            //  System.out.println(e.getMessage());
        }


        return isValid;
    }

    public void setPaymentChecker(PaymentChecker paymentChecker) {
        this.paymentChecker = paymentChecker;
    }
}
