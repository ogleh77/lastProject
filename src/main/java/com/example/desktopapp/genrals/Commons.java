package com.example.desktopapp.genrals;

import animatefx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Commons {
    private BorderPane borderPane;
    private SlideInRight slideInRight;
    private final SlideInLeft slideInLeft;

    private FadeIn fadeIn;

    public Commons() {
        this.slideInRight = new SlideInRight();
        this.slideInLeft = new SlideInLeft();
        this.fadeIn = new FadeIn();
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

    //Implement clos pane insha Allah
    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }


}
