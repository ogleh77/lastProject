package com.example.desktopapp.controlls.service;

import com.example.desktopapp.Common;
import com.example.desktopapp.controlls.PaymentController;
import com.example.desktopapp.entities.Customers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OutDatedController extends Common {

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
    private BorderPane borderPane;
    private Customers customer;


    @FXML
    void updateHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/payments.fxml", borderPane, null, null, null);

        PaymentController controller = loader.getController();
        controller.setCustomers(customer);
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;

        fullName.setText(customer.getFirstName());
        phone.setText(customer.getPhone());
        customerID.setText(String.valueOf(customer.getCustomerId()));
        shift.setText("Morning");
//        if (customer.getImage() != null) {
//            imageView.setImage(new Image(customer.getImage()));
//        }
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }
}
