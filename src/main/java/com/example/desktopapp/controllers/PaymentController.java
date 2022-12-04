package com.example.desktopapp.controllers;

import animatefx.animation.Shake;
import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.entity.services.Box;
import com.example.desktopapp.models.CustomerDTO;
import com.example.desktopapp.services.CommonClass;
import com.example.desktopapp.services.PaymentChecker;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.SQLException;
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

    private Customers customer;

    private double fitnessCost;
    private double poxingCost;
    private double vipBoxCost;
    private double currentCost;

    public PaymentController() {


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Box box = new Box("remove vip box");
        Platform.runLater(() -> {
            boxChooser.setItems(paymentChecker.getCurrentGym().getVipBoxes());
            boxChooser.getItems().add(box);

            fitnessCost = paymentChecker.getCurrentGym().getFitnessCost();
            poxingCost = paymentChecker.getCurrentGym().getPoxingCost();
            vipBoxCost = paymentChecker.getCurrentGym().getBoxCost();
            expDate.setValue(LocalDate.now().plusDays(30));
            paidBy.setItems(super.paidBy);
            currentCost = fitnessCost;
            amountPaid.setText(String.valueOf(currentCost));
        });

        //---------------------Validations----------------------

        boxChooser.valueProperty().addListener((observable, oldValue, newValue) -> {
            //Stop the user to name a box into remove or something insha Allah
            if ((oldValue == null || oldValue.getBoxName().matches("re.*")) && !newValue.getBoxName().matches("re.*")) {
                currentCost += vipBoxCost;
            } else if (oldValue != null && boxChooser.getValue().getBoxName().matches("re.*")) {
                currentCost -= vipBoxCost;
            }
            amountPaid.setText(String.valueOf(currentCost));
        });

        poxing.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (poxing.isSelected()) {
                currentCost += poxingCost;
            } else {
                currentCost -= poxingCost;
            }
            amountPaid.setText(String.valueOf((currentCost)));


        });


    }

    @FXML
    void saveHandler(ActionEvent event) {
        try {
            if (validateDiscount() == null && validatePaidBy() == null) {

                double _discount = ((!discount.getText().isEmpty() || !discount.getText().isBlank())) ? Double.parseDouble(discount.getText()) : 0;

                currentCost -= _discount;


                Payments payment = new Payments(expDate.getValue(), currentCost, paidBy.getValue(), _discount, poxing.isSelected(),
                        customer.getPhone());
                customer.getPayments().add(0, payment);

                //check if payment has a box
                if (boxChooser.getValue() != null && !boxChooser.getValue().getBoxName().contains("re")) {
                    payment.setBox(boxChooser.getValue());
                }
                //If the customer is new one create the customer and add a payment
                if (customer.getPayments().size() == 0) {
                    CustomerDTO.insertCustomerWithPayment(customer);

                    paymentChecker.getAllCustomers().add(0, customer);

                    messageAlert("Created", "new customer with payment created",
                            Alert.AlertType.INFORMATION);
                } else {
                    //If the customer was existed update and created only new payment

                    CustomerDTO.makePayment(customer);
                    messageAlert("Updated", "new payment created for " + customer.getFirstName(),
                            Alert.AlertType.INFORMATION);
                    System.out.println("He is already memeber");
                }

            }
        } catch (SQLException e) {
            messageAlert("Error", "error caused by " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }


    }

    @FXML
    void resetHandler(ActionEvent event) {


    }

    // -----------------helper methods-----------------------

    private String validatePaidBy() {
        if (paidBy.getValue() == null) {
            new Shake(paidBy).play();
            return "Error";
        }
        return null;
    }

    private String validateDiscount() {

        if ((!discount.getText().isEmpty() || !discount.getText().isBlank())) {
            if (!discount.getText().matches("[0-9]*")) {
                discountValidation.setVisible(true);
                discountValidation.setText("discount must be digits only ");
                return "error";
            } else {

                double _discount = Double.parseDouble(discount.getText());

                double maxDiscount = 1.0;
                if (_discount > maxDiscount) {
                    discountValidation.setVisible(true);
                    discountValidation.setText("discount can't greater then max discount of $" + maxDiscount);
                    return "error";
                } else {
                    discountValidation.setVisible(false);
                    discountValidation.setText(null);
                    return null;
                }
            }
        }

        return null;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
        firstName.setText(customer.getFirstName());
        middleName.setText(customer.getMiddleName());
        lastName.setText(customer.getLastName());

        phone.setText(customer.getPhone());
        if (customer.getGander().equals("Male")) {
            male.setSelected(true);
        } else {
            female.setSelected(true);
        }
    }

    @Override
    public void setPaymentChecker(PaymentChecker paymentChecker) {
        this.paymentChecker = paymentChecker;
    }
}
