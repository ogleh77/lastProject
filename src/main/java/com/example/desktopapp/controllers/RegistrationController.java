package com.example.desktopapp.controllers;

import com.example.desktopapp.genrals.Commons;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController extends Commons implements Initializable {
    @FXML
    private TextField address;

    @FXML
    private JFXRadioButton female;

    @FXML
    private TextField firstName;

    @FXML
    private VBox imagePane;

    @FXML
    private ImageView imgView;

    @FXML
    private ImageView imgView2;

    @FXML
    private ImageView imgView3;

    @FXML
    private TextField lastName;

    @FXML
    private JFXRadioButton male;

    @FXML
    private TextField middleName;

    @FXML
    private TextField phone;

    @FXML
    private ComboBox<String> shift;

    @FXML
    private TextField weight;
    private ObservableList<Control> mandatoryFields;
    ToggleGroup genderGroup;

    public RegistrationController() {
        mandatoryFields = FXCollections.observableArrayList();
        genderGroup = new ToggleGroup();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        shift.setItems(super.shift);
        Platform.runLater(() -> {
            mandatoryFields.addAll(firstName, middleName, lastName, phone, shift);

        });
    }

    @FXML
    void imageUploadHandler(ActionEvent event) {

    }

    @FXML
    void resetHandler(ActionEvent event) {

    }

    @FXML
    void stepTwoHandler(ActionEvent event) {

        if (!isValid(mandatoryFields, genderGroup)) {
            System.out.println("Not valid...");
        } else {
            System.out.println("Valid");
        }
    }
}
