package com.example.desktopapp.controllers.info;

import com.example.desktopapp.controllers.RegistrationController;
import com.example.desktopapp.controllers.service.PaymentController;
import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.helpers.CommonClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

public class OutDatedController extends CommonClass implements Initializable {

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
    void updateHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = openWindow("/com/example/desktopapp/views/services/payments.fxml", borderPane, null, null, null);
        PaymentController controller = loader.getController();
        controller.setPaymentChecker(paymentChecker);
        controller.setBorderPane(borderPane);
        controller.setCustomer(customer);
    }

    @Override
    public void setCustomer(Customers customer) {
        super.customer = customer;
        fullName.setText(customer.getFirstName() + " " + customer.getMiddleName() + " " + customer.getLastName());
        shift.setText(customer.getShift());

        phone.setText(customer.getPhone());

        LocalDate exp = customer.getPayments().get(0).getExpDate();
        LocalDate pendingDate = LocalDate.now();
        Period period = Period.between(pendingDate, exp);

        duration.setText(period.getDays() + " days before");
        try {
            if (customer.getImage() != null) {
                imageView.setImage(new Image(new FileInputStream(customer.getImage())));
            }
        } catch (Exception e) {

            messageAlert("image error",
                    "error " + customer.getFirstName(), Alert.AlertType.ERROR);
        }


    }

    @Override
    public void setBorderPane(BorderPane borderPane) {
        super.setBorderPane(borderPane);
    }
}
