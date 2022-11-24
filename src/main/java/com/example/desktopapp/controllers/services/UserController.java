package com.example.desktopapp.controllers.services;

import com.example.desktopapp.entities.services.Users;
import com.example.desktopapp.models.UsersDTO;
import com.example.desktopapp.services.CommonClass;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserController extends CommonClass implements Initializable {

    @FXML
    private JFXRadioButton admin;

    @FXML
    private JFXRadioButton female;

    @FXML
    private TextField firstname;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField lastname;

    @FXML
    private JFXRadioButton male;

    @FXML
    private PasswordField password;

    @FXML
    private TextField phone;

    @FXML
    private ComboBox<String> shift;

    @FXML
    private JFXRadioButton superAdmin;

    @FXML
    private JFXRadioButton user;

    @FXML
    private TextField username;

    private File selectedFile;
    private ToggleGroup roleToggle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roleToggle = new ToggleGroup();
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        shift.setItems(super.shift);
        superAdmin.setToggleGroup(roleToggle);
        user.setToggleGroup(roleToggle);
        admin.setToggleGroup(roleToggle);
        user.setSelected(true);
        mandatoryFields.addAll(firstname, lastname, username, password, phone, shift);
    }

    @FXML
    void createUser(ActionEvent event) {

        if (!isValid(mandatoryFields, genderGroup)) {
            System.out.println("Not valid..");
        } else {
            try {
                String gander = male.isSelected() ? "Male" : "Female";
                String image = selectedFile != null ? selectedFile.getAbsolutePath() : null;
                String role;
                if (superAdmin.isSelected()) {
                    role = "SuperAdmin";
                } else if (admin.isSelected()) {
                    role = "Admin";
                } else {
                    role = "user";
                }
                Users user = new Users(firstname.getText(), lastname.getText(), phone.getText(),
                        gander, shift.getValue(), username.getText().trim(), password.getText().trim(), image, role);

                UsersDTO.addUser(user);
                messageAlert("Created", "You seccessfully created new user", Alert.AlertType.INFORMATION);

            } catch (SQLException e) {
                messageAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }


    @FXML
    void imageUploader(ActionEvent event) {
        imageUpload();

        try {

            if (selectedFile != null) {
                Image image = new Image(new FileInputStream(selectedFile.getAbsolutePath()));
                imageView.setImage(image);
                imageView.setX(50);
                imageView.setY(25);
                imageView.setFitHeight(166);
                imageView.setFitWidth(200);
                // imageView.setPreserveRatio(true);
            }
        } catch (FileNotFoundException exception) {

            //Throw exce ption
            exception.printStackTrace();
        }
    }

    private void imageUpload() {
        FileChooser chooser = new FileChooser();
        selectedFile = chooser.showOpenDialog(null);
        try {
            if (selectedFile != null) {
                Image image = new Image(new FileInputStream(selectedFile.getAbsolutePath()));
                imageView.setImage(image);
                imageView.setX(50);
                imageView.setY(25);
                imageView.setFitHeight(166);
                imageView.setFitWidth(200);
                // imageView.setPreserveRatio(true);
            }
        } catch (
                FileNotFoundException exception) {

            //Throw exce ption
            exception.printStackTrace();
        }
    }
}
