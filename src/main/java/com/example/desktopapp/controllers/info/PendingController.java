package com.example.desktopapp.controllers.info;

import com.example.desktopapp.entity.Payments;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

public class PendingController implements Initializable {

    @FXML
    private Label daysRemain;

    @FXML
    private Label expDate;

    @FXML
    private Label paymentDate;

    private Stage pendingStage;

    private Payments payment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            pendingStage = (Stage) paymentDate.getScene().getWindow();
        });
    }

    @FXML
    void pendBtn(ActionEvent event) {

        System.out.println("Pended..");
    }

    @FXML
    void cancelBtn(ActionEvent event) {
        pendingStage.close();
    }

    public void setPayment(Payments payment) {
        this.payment = payment;

        paymentDate.setText(payment.getPaymentDate());
        expDate.setText(payment.getExpDate().toString());

        LocalDate exp = payment.getExpDate();
        LocalDate pendingDate = LocalDate.now();
        Period period = Period.between(pendingDate, exp);

        daysRemain.setText(period.getDays() + " days remind");

    }
}
