package com.example.desktopapp.genrals;

import animatefx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Control;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Commons {
    private BorderPane borderPane;
    private SlideInRight slideInRight;
    private final SlideInLeft slideInLeft;

    public final ObservableList<String> shift;
    public final ObservableList<String> paidBy;
    private FadeIn fadeIn;
    private Shake shake;

    public Commons() {
        this.slideInRight = new SlideInRight();
        this.slideInLeft = new SlideInLeft();
        this.fadeIn = new FadeIn();
        this.shake = new Shake();
        shift = FXCollections.observableArrayList();
        paidBy = FXCollections.observableArrayList();
        this.shift.addAll("Morning", "Noon", "Afternoon", "Night");
        this.paidBy.addAll("eDahab", "Zaad service", "other");
    }

    protected FXMLLoader openWindow(String url, BorderPane borderPane, VBox sidePane, HBox menu, HBox profile) throws IOException {

        if (!sidePane.isVisible()) {

            sidePane.setVisible(true);
            slideInLeft.setNode(sidePane);
            slideInLeft.play();
            borderPane.setLeft(sidePane);
        }
        //side menu
        menu.setVisible(true);
        fadeIn.setNode(menu);
        slideInLeft.playOnFinished(fadeIn);
        fadeIn.play();

        //top profile
        profile.setVisible(true);
        fadeIn.setNode(profile);
        fadeIn.play();
//
//
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));

        AnchorPane anchorPane = loader.load();
        slideInRight.setNode(anchorPane);
        slideInRight.play();
        borderPane.setCenter(anchorPane);

        return loader;
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
            System.out.println(e.getMessage());
        }


        return isValid;
    }

    //Implement clos pane insha Allah
    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }


}
