package com.example.desktopapp.controllers;

import com.example.desktopapp.entities.Box;
import com.example.desktopapp.entities.Customers;
import com.example.desktopapp.entities.Payments;
import com.example.desktopapp.models.PaymentDTO;
import com.example.desktopapp.services.CommonClass;
import com.example.desktopapp.services.PaymentChecker;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
    private VBox imagePane;

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
        expDate.setValue(LocalDate.now().plusDays(30));
        paidBy.setItems(super.paidBy);

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
    void saveHandler(ActionEvent event) {

        if (validateDiscount() == null) {
            double _discount = ((!discount.getText().isEmpty() || !discount.getText().isBlank())) ? Double.parseDouble(discount.getText()) : 0;

            currentCost -= _discount;

            Payments payment = new Payments(expDate.getValue(), currentCost, paidBy.getValue(),
                    _discount, poxing.isSelected(), customer.getPhone());
            System.out.println(payment);

            try {
                if (customer.getPayment() == null) {
                    System.out.println("new payment has been inserting");
                    //Add payment to get payment info true the customer in PaymentDto class"
                    customer.setPayment(payment);
                    PaymentDTO.insertPayment(customer);
                    Alert alert = message(
                            Alert.AlertType.INFORMATION,
                            "Waxaad ku gulaystay diwaan gelinta payment cusub",
                            "Created new Payment"
                    );
                    alert.showAndWait();
                } else {

                    customer.setPayment(payment);
                    PaymentDTO.updatePayment(customer);
                    System.out.println("Existing payment has been updated");
                    Alert alert = message(
                            Alert.AlertType.INFORMATION,
                            "Waxaad ku gulaystay update garaynta" +
                                    " lacag bixin hore",
                            "Updated Payment"
                    );
                    alert.showAndWait();
                }

            } catch (SQLException e) {
                Alert alert = message(
                        Alert.AlertType.ERROR,
                        "Khalad ba ka dhacay " + e.getMessage(),
                        "Error"
                );
                alert.showAndWait();
            }


        }

    }


    @Override
    public void setPaymentChecker(PaymentChecker paymentChecker) {
        try {
            for (Box box : paymentChecker.getCurrentGym().getVipBoxes()) {
                if (box.isReady()) {
                    boxChooser.getItems().add(box);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setCustomer(Customers customer) {
        this.customer = customer;
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
}
