package com.example.desktopapp.services;

import animatefx.animation.FadeIn;
import animatefx.animation.Shake;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import com.example.desktopapp.HelloApplication;
import com.example.desktopapp.entities.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class CommonClass {
    protected final ToggleGroup genderGroup;
    public final ObservableList<Control> mandatoryFields;

    public final ObservableList<String> shift;
    public final ObservableList<String> paidBy;

    public final Shake shake;
    private final SlideInRight slideInRight;
    private final SlideInLeft slideInLeft;
    private final FadeIn fadeIn;

    protected BorderPane borderPane;
    private File selectedFile;
    private PaymentChecker paymentChecker;

    private Customers customer;

    public CommonClass() {
        this.genderGroup = new ToggleGroup();
        this.mandatoryFields = FXCollections.observableArrayList();
        shift = FXCollections.observableArrayList();
        paidBy = FXCollections.observableArrayList();
        this.shift.addAll("Morning", "Noon", "Afternoon", "Night");
        this.paidBy.addAll("eDahab", "Zaad service", "other");
        this.shake = new Shake();
        this.slideInRight = new SlideInRight();
        this.slideInLeft = new SlideInLeft();
        this.fadeIn = new FadeIn();
    }

    public boolean isValid(ObservableList<Control> mandatoryFields, ToggleGroup group) {
        boolean isValid = true;

        try {

            for (Control control : mandatoryFields) {
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

    protected Alert messageAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
        return alert;
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

    public void setPaymentChecker(PaymentChecker paymentChecker) throws SQLException {
        this.paymentChecker = paymentChecker;
    }


    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }
}
