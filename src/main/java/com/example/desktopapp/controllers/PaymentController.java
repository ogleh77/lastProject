package com.example.desktopapp.controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    @FXML
    private TextField amountPaid;

    @FXML
    private ComboBox<?> boxChooser;

    @FXML
    private JFXRadioButton female;

    @FXML
    private TextField firstName;

    @FXML
    private VBox imagePane;

    @FXML
    private ImageView imgView;

    @FXML
    private TextField lastName;

    @FXML
    private JFXRadioButton male;

    @FXML
    private TextField middleName;

    @FXML
    private ComboBox<String> paidBy;

    @FXML
    private DatePicker expDate;

    @FXML
    private TextField phone;

    @FXML
    private JFXCheckBox poxing;

    @FXML
    private ComboBox<String> shift;

    @FXML
    private TextArea txtComment;

    @FXML
    private TextField weight;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void saveHandler(ActionEvent event) {

    }
}
