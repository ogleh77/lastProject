package com.example.desktopapp.controllers;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.entity.services.Box;
import com.example.desktopapp.models.CustomerDTO;
import com.example.desktopapp.services.CommonClass;
import com.example.desktopapp.services.PaymentChecker;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    private PaymentChecker paymentChecker;

    private double fitnessCost = 12.0;
    private double poxingCost = 2.0;
    private final double vipBoxCost = 3.0;
    private double currentCost = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Box> boxes = FXCollections.observableArrayList();
        boxes.add(new Box(1, "Box 1", true));
        boxes.add(new Box(2, "Box 2", true));
        boxes.add(new Box(3, "Box 3", true));
        boxes.add(new Box(0, "remove box", true));

        expDate.setValue(LocalDate.now().minusDays(30));
        paidBy.setItems(super.paidBy);
        currentCost = fitnessCost;
        amountPaid.setText(String.valueOf(currentCost));

        //--------------------operations------------------------
        boxChooser.setItems(boxes);

        currentCost = fitnessCost;

        amountPaid.setText(String.valueOf(currentCost));

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
        if (validateDiscount() == null) {
            double _discount = ((!discount.getText().isEmpty() || !discount.getText().isBlank())) ? Double.parseDouble(discount.getText()) : 0;

            currentCost -= _discount;


            Payments payment = new Payments(expDate.getValue(), currentCost, paidBy.getValue(), _discount, poxing.isSelected(),
                    String.valueOf(customer.getCustomerId()));

            if (boxChooser.getValue() != null) {
                Box box = boxChooser.getValue();
                payment.setBox(box);
            }

            customer.getPayments().add(0, payment);

            try {
                CustomerDTO.insertCustomerWithPayment(customer);
                customer.getPayments().add(payment);
                paymentChecker.getAllCustomers().add(0, customer);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    // -----------------helper methods-----------------------


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

        expDate.setValue(LocalDate.now().plusDays(30));


        for (Payments payment : customer.getPayments()) {

            if (payment != null) {
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

    @Override
    public void setPaymentChecker(PaymentChecker paymentChecker) {
        this.paymentChecker = paymentChecker;
    }
}
