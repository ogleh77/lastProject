package com.example.desktopapp.controllers;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.entity.services.Users;
import com.example.desktopapp.models.CustomerDTO;
import com.example.desktopapp.services.CommonClass;
import com.example.desktopapp.services.PaymentChecker;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private JFXButton uploadImageBtn;
    @FXML
    private JFXButton updateBtn;
    @FXML
    private JFXButton paymentBtn;
    @FXML
    private Label headerInfo;
    private Customers customer;
    private Payments payment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        shift.setItems(super.shift);
        Platform.runLater(() -> {
            customerId.setText(String.valueOf(CustomerDTO.id));
            mandatoryFields.addAll(firstName, middleName, lastName, phone, shift);

            if (payment == null) {
                paymentBtn.setVisible(true);
            }
        });


    }


    @FXML
    void updateHandler(ActionEvent event) throws IOException {

        if (isValid(mandatoryFields, genderGroup) && phoneCheck() == null) {
            try {
                imageForgot(selectedFile);
                String gander = male.isSelected() ? "Male" : "Female";
                String _address = address.getText() != null ? address.getText().trim() : null;
                double _weight = ((!weight.getText().isEmpty() || !weight.getText().isBlank())) ? Double.parseDouble(weight.getText().trim()) : 65.0;
                String image = selectedFile != null ? selectedFile.getAbsolutePath() : null;

                if (customer == null) {
                    Customers newCustomer = new Customers(11, firstName.getText(), middleName.getText(), lastName.getText(),
                            phone.getText(), gander, shift.getValue(), _address, image, _weight,
                            paymentChecker.getActiveUser().getUsername());

                    nextWindow(newCustomer);

                } else {
                    Customers updatedCustomer = new Customers(customer.getCustomerId(), firstName.getText(),
                            middleName.getText(), lastName.getText(), phone.getText(), gander, shift.getValue(),
                            _address, image, _weight, customer.getWhoAdded());

                    if (payment != null) {
                        CustomerDTO.updateCustomer(updatedCustomer);
                    }

                }


            } catch (SQLException e) {

            }
        }

    }

    @FXML
    void paymentHandler(ActionEvent event) {

//        for (Customers customer : paymentChecker.getAllCustomers()) {
//
//            System.out.println("Name " + customer.getFirstName() + " " + customer.getLastName());
//            System.out.println("------------");
//            System.out.println(customer.getPayments());
//            System.out.println("------------====-----------");
//        }

        System.out.println(payment);
    }

    @FXML
    void imageUploadHandler(ActionEvent event) {
        imageUpload();
    }


    //------------------Helper merhods are down there------------------


    //------------------get the payment window if the current customer haven't a payment------------------
    private void nextWindow(Customers customer) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/payments.fxml", borderPane, null, null, null);
        PaymentController controller = loader.getController();
        controller.setCustomer(customer);
        controller.setBorderPane(borderPane);
        controller.setPaymentChecker(paymentChecker);
    }


    //------------------Choose an image from disk and set if the selected image is not null------------------
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
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }


    //------------------Tell the user the importance of the image if he/she forgot------------------
    private void imageForgot(File file) {
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


    //------------------check if the input phone is grater than 10 || less then 7------------------
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

    // an overiding method wich fetchin the data from home pane
    @Override
    public void setCustomer(Customers customer) {
        this.customer = customer;
        firstName.setText(customer.getFirstName());
        middleName.setText(customer.getMiddleName());
        lastName.setText(customer.getLastName());
        phone.setText(customer.getPhone());
        weight.setText(String.valueOf(customer.getWeight()));
        shift.setValue(customer.getShift());
        address.setText(customer.getAddress() != null ? customer.getAddress() : "No address");

        if (customer.getGander().equals("Male")) {
            male.setSelected(true);
        } else {
            female.setSelected(true);
        }

        //------------------set image if exist--------------------
//        if (customer.getImage() != null) {
//            imgView.setImage(new Image(customer.getImage()));
//        }

        for (Payments payment : customer.getPayments()) {
            if (payment.isOnline() && payment.getExpDate().isAfter(LocalDate.now())) {
                this.payment = payment;
            }
        }

        //if customer is not null tell the user meaning full data
        paymentBtn.setText("Update");
        uploadImageBtn.setText("Change image");
        headerInfo.setText("UPDATING EXISTED CUSTOMER INFO");
    }


}
