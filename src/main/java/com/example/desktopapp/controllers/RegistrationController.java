package com.example.desktopapp.controllers;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.helpers.CommonClass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegistrationController extends CommonClass implements Initializable {
    @FXML
    private TextField address;

    @FXML
    private Label customerId;

    @FXML
    private JFXRadioButton female;

    @FXML
    private TextField firstName;

    @FXML
    private ImageView imgView;

    @FXML
    private TextField lastName;

    @FXML
    private JFXRadioButton male;

    @FXML
    private Label messageValidation;

    @FXML
    private TextField middleName;

    @FXML
    private TextField phone;

    @FXML
    private ComboBox<String> shift;

    @FXML
    private TextField weight;
    @FXML
    private Label headerInfo;
    @FXML
    private JFXButton registerBtn;
    private Customers customer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            male.setToggleGroup(genderGroup);
            female.setToggleGroup(genderGroup);
            shift.setItems(super.shift);
            mandatoryFields.addAll(firstName, middleName, lastName, phone, shift);


            if (customer == null) {
                registerBtn.setText("Step two");
            }
        });

    }

    @FXML
    void imageUploadHandler(ActionEvent event) {

        try {
            imageUpload();
        } catch (FileNotFoundException fileNotFoundException) {
            messageAlert("FileNotFoundException", "Error " + fileNotFoundException.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @FXML
    void resetHandler(ActionEvent event) {

    }


    @FXML
    void stepTwoHandler(ActionEvent event) {
        if (customer == null) {
            System.out.println("New customer added");
        } else {
            System.out.println(customer);
        }
    }


    //-----------------Helper methods------------


    private void nextWindow(Customers customer) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/payments.fxml", borderPane, null, null, null);
        //PaymentController controller = loader.getController();
//        controller.setCustomer(customer);
//        controller.setBorderPane(borderPane);
//        controller.setPaymentChecker(paymentChecker);
    }


    private void imageUpload() throws FileNotFoundException {
        FileChooser chooser = new FileChooser();
        selectedFile = chooser.showOpenDialog(null);
        try {
            if (selectedFile != null) {
                Image image = new Image(new FileInputStream(selectedFile.getAbsolutePath()));
                imgView.setImage(image);
                imgView.setX(50);
                imgView.setY(25);
                imgView.setFitHeight(166);
                imgView.setFitWidth(200);
            }
        } catch (FileNotFoundException exception) {
            throw exception;
        }

    }

//    private void imageForgot(File file) throws FileNotFoundException {
//        if (file == null) {
//            Alert alert = message(Alert.AlertType.CONFIRMATION, "Sawirkii maad ilowdey mise ogaan baad u dhaftay", "Forgot images");
//            Optional<ButtonType> config = alert.showAndWait();
//            if (config.get().getButtonData().isDefaultButton()) {
//                System.out.println("Ok pressed..");
//                alert.close();
//                imageUpload();
//            }
//        }
//    }


    private String phoneCheck() {
        if (!phone.getText().matches("[0-9]*")) {
            messageValidation.setVisible(true);
            messageValidation.setText("phone must be digits only");
            return "error";
        } else if (!phone.getText().matches("^\\d{7}")) {
            messageValidation.setVisible(true);
            messageValidation.setText("phone can't be greater than 7 digits or less");
            return "error";
        }
        messageValidation.setVisible(false);
        return null;

    }

    private void imageForgot(File file, String message) throws FileNotFoundException {
        if (file == null) {
            Alert alert = message(Alert.AlertType.CONFIRMATION, "Sawirkii maad ilowdey mise ogaan baad u dhaftay", "Forgot images");
            Optional<ButtonType> config = alert.showAndWait();
            if (config.get().getButtonData().isDefaultButton()) {
                System.out.println("Ok pressed..");
                alert.close();
                imageUpload();
            }
        }
    }

    @Override
    public void setCustomer(Customers customer) {
        this.customer = customer;

        firstName.setText(customer.getFirstName());
        middleName.setText(customer.getFirstName());
        lastName.setText(customer.getFirstName());

        phone.setText(customer.getPhone());
        weight.setText(String.valueOf(customer.getWeight()));
        shift.setValue(customer.getShift());
        address.setText(customer.getAddress() != null ? customer.getAddress() : "No address");

        if (customer.getGander().equals("Male")) {
            male.setSelected(true);
        } else {
            female.setSelected(true);
        }
        weight.setText(String.valueOf(customer.getWeight()));
        shift.setValue(customer.getShift());
        address.setText(customer.getAddress() != null ? customer.getAddress() : "No address");

        if (customer.getImage() != null) {
            imgView.setImage(new Image(customer.getImage()));
        }

        headerInfo.setText("UPDATING EXISTED CUSTOMER INFO");

    }
}
