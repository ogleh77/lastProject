package com.example.desktopapp.controllers.service;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.entity.serices.Box;
import com.example.desktopapp.helpers.CommonClass;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
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
    private Label discountValidtaion;

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

    private double fitnessCost = 12.0;
    private double poxingCost = 2.0;
    private final double vipBoxCost = 3.0;

    private double currentCost = 0;
    private Customers customer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Platform.runLater(() -> {
            boxChooser.getItems().add(new Box(0, "remove box", true));
            expDate.setValue(LocalDate.now().minusDays(30));
            paidBy.setItems(super.paidBy);
        });

        currentCost = fitnessCost;
        amountPaid.setText(String.valueOf(currentCost));

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
    void saveHandler(ActionEvent event) throws SQLException {


        if (customer.getPayments().isEmpty()) {
            System.out.println("New customer with payment added");
        } else {
            System.out.println("New payment created by " + customer.getFirstName());
        }

//

    }

    private String validateDiscount() {

        if ((!discount.getText().isEmpty() || !discount.getText().isBlank())) {
            if (!discount.getText().matches("[0-9]*")) {
                discountValidtaion.setVisible(true);
                discountValidtaion.setText("discount must be digits only ");
                return "error";
            } else {

                double _discount = Double.parseDouble(discount.getText());

                double maxDiscount = 1.0;
                if (_discount > maxDiscount) {
                    discountValidtaion.setVisible(true);
                    discountValidtaion.setText("discount can't greater then max discount of $" + maxDiscount);
                    return "error";
                } else {
                    discountValidtaion.setVisible(false);
                    discountValidtaion.setText(null);
                    return null;
                }
            }
        }

        return null;
    }


    public void setCustomer(Customers customer) {
        this.customer = customer;
        firstName.setText(customer.getFirstName());
        middleName.setText(customer.getFirstName());
        lastName.setText(customer.getFirstName());
        middleName.setText(customer.getMiddleName());
        lastName.setText(customer.getLastName());

        phone.setText(customer.getPhone());
        if (customer.getGander().equals("Male")) {
            male.setSelected(true);
        } else {
            female.setSelected(true);
        }

        expDate.setValue(LocalDate.now().plusDays(30));
        System.out.println(customer);

    }
}