package com.example.desktopapp.controllers;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.services.Users;
import com.example.desktopapp.models.CustomerDTO;
import com.example.desktopapp.services.CommonClass;
import com.example.desktopapp.services.PaymentChecker;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        shift.setItems(super.shift);
        Platform.runLater(() -> {
            customerId.setText(String.valueOf(CustomerDTO.id));
            mandatoryFields.addAll(firstName, middleName, lastName, phone, shift);
        });
    }


    @FXML
    void stepTwoHandler(ActionEvent event) {
        try {
            if (isValid(mandatoryFields, genderGroup) && phoneCheck() == null) {

                imageForgot(selectedFile);
                String gander = male.isSelected() ? "Male" : "Female";
                String _address = address.getText() != null ? address.getText().trim() : null;
                double _weight = ((!weight.getText().isEmpty() || !weight.getText().isBlank())) ? Double.parseDouble(weight.getText().trim()) : 65.0;
                String image = selectedFile != null ? selectedFile.getAbsolutePath() : null;

                Customers customer = new Customers(0, firstName.getText(), middleName.getText(), lastName.getText(),
                        phone.getText(), gander, shift.getValue(), _address, image, _weight, "Ogleh");


                nextWindow(paymentChecker.getActiveCustomers().get(0));

                System.out.println(customer);
                //     CustomerDTO.insertCustomer(customer);


            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = message(
                    Alert.AlertType.ERROR,
                    e.getMessage(),
                    "Error"
            );
            alert.showAndWait();
        }
    }

    @FXML
    void resetHandler(ActionEvent event) {

    }

    @FXML
    void imageUploadHandler(ActionEvent event) {

    }


    private void nextWindow(Customers customer) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/payments.fxml", borderPane, null, null, null);
        PaymentController controller = loader.getController();
        controller.setCustomer(customer);
        controller.setBorderPane(borderPane);
        controller.setPaymentChecker(paymentChecker);
    }

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

    private void imageForgot(File file) {
        if (file == null) {
            Alert alert = message(
                    Alert.AlertType.CONFIRMATION,
                    "Sawirkii maad ilowdey mise ogaan baad u dhaftay",
                    "Forgot images"
            );

            Optional<ButtonType> config = alert.showAndWait();
            if (config.get().getButtonData().isDefaultButton()) {
                System.out.println("Ok pressed..");
                alert.close();
                imageUpload();

            }
        }
    }

    private void imageUpload() {
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
                // imageView.setPreserveRatio(true);
            }
        } catch (
                FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

}
