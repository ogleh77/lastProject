package com.example.desktopapp.controllers;

import com.example.desktopapp.entities.Customers;

import com.example.desktopapp.services.CommonClass;
import com.example.desktopapp.services.PaymentChecker;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends CommonClass implements Initializable {

    @FXML
    private TableColumn<Customers, Integer> customerId;

    @FXML
    private TableColumn<Customers, String> datePaid;

    @FXML
    private TableColumn<Customers, String> expDate;

    @FXML
    private TableColumn<Customers, String> fullName;

    @FXML
    private TableColumn<Customers, String> gander;

    @FXML
    private TableColumn<Customers, JFXButton> informationBtn;

    @FXML
    private TableColumn<Customers, String> phone;

    @FXML
    private TextField search;

    @FXML
    private TableView<Customers> tableView;

    @FXML
    private TableColumn<Customers, JFXButton> update;

    @FXML
    private Label usersCount;
    @FXML
    private Label outDatedCount;
    @FXML
    private Label activeCount;

    private PaymentChecker paymentChecker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            System.out.println("Wait");
            paymentChecker.FetchAllCustomers.setOnSucceeded(e -> {
                System.out.println("Done");
                initTable();
            });

        });


    }

    private void initTable() {
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        fullName.setCellValueFactory(customers -> new SimpleStringProperty(
                customers.getValue().getFirstName() + " " + customers.getValue().getMiddleName()
                        + " " + customers.getValue().getLastName()));

        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        gander.setCellValueFactory(new PropertyValueFactory<>("gander"));

        datePaid.setCellValueFactory(customers -> new SimpleStringProperty(
                customers.getValue().getPayments().get(0).getPaymentDate()));

        expDate.setCellValueFactory(customers -> new SimpleStringProperty(
                customers.getValue().getPayments().get(0).getExpDate().toString()));

        informationBtn.setCellValueFactory(new PropertyValueFactory<>("information"));
        update.setCellValueFactory(new PropertyValueFactory<>("update"));
        tableView.setItems(paymentChecker.getAllCustomers());

    }

    @Override
    public void setPaymentChecker(PaymentChecker paymentChecker) {
        this.paymentChecker = paymentChecker;
        System.out.println("The checker passed to home" + paymentChecker);
//
        Thread thread = new Thread(paymentChecker.FetchAllCustomers);
        thread.setDaemon(true);
        thread.start();
    }
}
