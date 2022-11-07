package com.example.desktopapp.controlls;

import com.example.desktopapp.Common;
import com.example.desktopapp.entities.Customers;
import com.example.desktopapp.models.PaymentChecker;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HomeController extends Common implements Initializable {
    @FXML
    private Label activeCount;

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

    private PaymentChecker checker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    private void initTable() throws SQLException {
        customerId.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("customerId"));
        fullName.setCellValueFactory(name -> new SimpleStringProperty(name.getValue().getFirstName() + " " + name.getValue().getMiddleName() + " " + name.getValue().getLastName()));
        phone.setCellValueFactory(new PropertyValueFactory<Customers, String>("lastName"));
        gander.setCellValueFactory(new PropertyValueFactory<Customers, String>("gander"));
        phone.setCellValueFactory(new PropertyValueFactory<Customers, String>("phone"));
        expDate.setCellValueFactory(expDate -> new SimpleStringProperty(expDate.getValue().getPayments().get(0).getExpDate().toString()));
        datePaid.setCellValueFactory(paidData -> new SimpleStringProperty(paidData.getValue().getPayments().get(0).getPaymentDate().toString()));
        update.setCellValueFactory(new PropertyValueFactory<Customers, JFXButton>("update"));
        informationBtn.setCellValueFactory(new PropertyValueFactory<Customers, JFXButton>("information"));

        tableView.setItems(checker.getActiveCustomers());
    }

    @Override
    public void setPaymentChecker(PaymentChecker paymentChecker) throws IOException {
        this.checker = paymentChecker;
        try {

            initTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
