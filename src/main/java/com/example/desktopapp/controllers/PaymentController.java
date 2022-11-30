package com.example.desktopapp.controllers;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.services.Box;
import com.example.desktopapp.services.CommonClass;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PaymentController extends CommonClass implements Initializable {
    @FXML
    private TextField amountPaid;

    @FXML
    private ComboBox<Box> boxChooser;

    @FXML
    private TextField discount;

    @FXML
    private Label discountValidation;

    @FXML
    private DatePicker expDate;

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
    private TextField middleName;
    @FXML
    private ComboBox<String> paidBy;
    @FXML
    private TextField phone;
    @FXML
    private JFXCheckBox poxing;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void saveHandler(ActionEvent event) {

    }


    public void setCustomer(Customers customer) {
        firstName.setText(customer.getFirstName());
        middleName.setText(customer.getFirstName());
        lastName.setText(customer.getFirstName());

        phone.setText(customer.getPhone());
        if (customer.getGander().equals("Male")) {
            male.setSelected(true);
        } else {
            female.setSelected(true);
        }

        expDate.setValue(LocalDate.now().plusDays(30));

        System.out.println(customer);

        if (customer.getPayment() != null) {
            paidBy.setValue(customer.getPayment().getPaidBy());
            discount.setText(String.valueOf(customer.getPayment().getDiscount()));
            amountPaid.setText(String.valueOf(customer.getPayment().getAmountPaid()));
            expDate.setValue(customer.getPayment().getExpDate());

            if (customer.getPayment().getBox() != null) {
                boxChooser.setValue(customer.getPayment().getBox());
            }

            if (customer.getPayment().isPoxing()) {
                poxing.setSelected(true);
            }
        }
    }
}
