package com.example.desktopapp.controllers;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.helpers.CommonClass;
import com.example.desktopapp.helpers.PaymentChecker;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends CommonClass implements Initializable {
    @FXML
    private TableColumn<Customers, Integer> customerId;

    @FXML
    private TableColumn<Customers, String> fullName;

    @FXML
    private TableColumn<Customers, String> gander;

    @FXML
    private TableColumn<Customers, JFXButton> informationBtn;

    @FXML
    private TableColumn<Customers, JFXButton> update;

    @FXML
    private TableColumn<Customers, String> phone;

    @FXML
    private TextField search;

    @FXML
    private TableView<Customers> tableView;

    private StackPane stackPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            //   System.out.println("Done " + paymentChecker.getAllCustomers());
        });
    }

    private void initTable() {
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        fullName.setCellValueFactory(customers -> new SimpleStringProperty(
                customers.getValue().firstname() + " " + customers.getValue().middleName()
                        + " " + customers.getValue().lastName()));

        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        gander.setCellValueFactory(new PropertyValueFactory<>("gander"));

        informationBtn.setCellValueFactory(new PropertyValueFactory<>("information"));
        update.setCellValueFactory(new PropertyValueFactory<>("update"));
    }


    @Override
    public void setPaymentChecker(PaymentChecker paymentChecker) {
        this.paymentChecker = paymentChecker;
    }


    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    @Override
    public void setBorderPane(BorderPane borderPane) {
        super.setBorderPane(borderPane);
    }
}
