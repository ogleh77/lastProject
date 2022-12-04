package com.example.desktopapp.controllers.service;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.services.CommonClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class OutdatedController extends CommonClass implements Initializable {

    @FXML
    private Label customerID;

    @FXML
    private Label duration;

    @FXML
    private Label fullName;

    @FXML
    private ImageView imageView;

    @FXML
    private Label phone;

    @FXML
    private Label shift;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void updateHandler(ActionEvent event) {

    }

    @Override
    public void setCustomer(Customers customer) {

        fullName.setText(customer.getFirstName() + " " + customer.getMiddleName() + " " + customer.getLastName());
        shift.setText(customer.getShift());
        phone.setText(customer.getPhone());
        duration.setText(customer.getPayments().get(0).getExpDate().toString());
        customerID.setText(String.valueOf(customer.getCustomerId()));
     }
}
