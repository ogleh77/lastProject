package com.example.desktopapp.controlls;

import com.example.desktopapp.Common;
import com.example.desktopapp.entities.Box;
import com.example.desktopapp.entities.Customers;
import com.example.desktopapp.entities.services.Gym;
import com.example.desktopapp.models.GymDAO;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PaymentController extends Common implements Initializable {
    @FXML
    private TextField amountPaid;

    @FXML
    private ComboBox<Box> boxChooser;

    @FXML
    private TextField discount;

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
    @FXML
    private Label discountValidtaion;
    private final Gym gym;
    private final double fitnessCost;
    private final double poxingCost;
    private final double vipBoxCost;
    private final double maxDiscount;

    private Customers customers;
    private LocalDate localDate = LocalDate.now();

    public PaymentController() throws SQLException {
        this.gym = GymDAO.getCurrentGym();
        this.fitnessCost = gym.getFitnessCost();
        this.poxingCost = gym.getPoxingCost();
        this.vipBoxCost = gym.getBoxCost();
        this.maxDiscount = gym.getMaxDiscount();
        System.out.println("Fitness cost : " + fitnessCost + " vipBox: "
                + vipBoxCost + " poxingCost: " + poxingCost + " max: " + maxDiscount + " " + gym.getVipBoxes());
    }

    @FXML
    void saveHandler(ActionEvent event) {


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        boxChooser.setItems(gym.getVipBoxes());
        boxChooser.getItems().add(new Box(0, "Remove", false));
        expDate.setValue(localDate.plusDays(30));
        discount.setText(String.valueOf(0));
        paidBy.setItems(super.paidBy);

        amountPaid.setText(String.valueOf(fitnessCost));


        discount.textProperty().addListener(new ChangeListener<String>() {
            double _amountPaid = Double.parseDouble(amountPaid.getText());
            final double currentAmount = _amountPaid;

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {


                if (!discount.getText().isBlank() && !discount.getText().isBlank()) {
                    System.out.println("Current ammount " + currentAmount);
                    amountPaid.setText(String.valueOf(currentAmount));
                    final double _discount = Double.parseDouble(discount.getText());

                    if (_discount > maxDiscount) {
                        discountValidtaion.setText("disocunt ku kama badan karo " + maxDiscount);
                        discountValidtaion.setVisible(true);

                    } else {
                        discountValidtaion.setVisible(false);
                        amountPaid.setText(String.valueOf(_amountPaid - _discount));
                    }
                }
            }
        });
//}
//            final double currentAmount = Double.parseDouble(amountPaid.getText());
//
//            final double _discount = Double.parseDouble(discount.getText());
//            double discounted = currentAmount - _discount;
//
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//
//                if (!discount.getText().isBlank() && !discount.getText().isBlank()) {
//
//                    if (_discount > maxDiscount) {
//
//                    } else {
//                        amountPaid.setText(String.valueOf(discounted));
//                    }
//                } else {
//                    amountPaid.setText(String.valueOf(Double.parseDouble(amountPaid.getText()) - currentAmount));
//                }
//            }
//        });
//
        boxChooser.valueProperty().addListener(new ChangeListener<>() {
            double currentVal = vipBoxCost + Double.parseDouble(amountPaid.getText());

            @Override
            public void changed(ObservableValue<? extends Box> observable, Box oldValue, Box newValue) {

                if (boxChooser.getValue().getBoxName().contains("Box")) {
                    amountPaid.setText(String.valueOf(currentVal));
                } else {
                    currentVal = Double.parseDouble(amountPaid.getText()) - vipBoxCost;
                    amountPaid.setText(String.valueOf(currentVal));
                    currentVal = vipBoxCost + Double.parseDouble(amountPaid.getText());
                }

            }
        });
        poxing.selectedProperty().addListener((observable, oldValue, newValue) -> {

            if (poxing.isSelected()) {
                amountPaid.setText(String.valueOf(Double.parseDouble(amountPaid.getText()) + poxingCost));

            } else {
                amountPaid.setText(String.valueOf(Double.parseDouble(amountPaid.getText()) - poxingCost));
            }
        });
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
        firstName.setText(customers.getFirstName());
        middleName.setText(customers.getMiddleName());
        lastName.setText(customers.getLastName());
        phone.setText(customers.getPhone());
        phone.setText(customers.getPhone());

        if (customers.getImage() != null) {
            imgView.setImage(new Image(customers.getImage()));
        }
        if (customers.getGander().equals("Male")) {
            male.setSelected(true);
        } else {
            female.setSelected(true);
        }
    }
}
