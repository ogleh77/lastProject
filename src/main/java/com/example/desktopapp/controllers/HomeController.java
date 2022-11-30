package com.example.desktopapp.controllers;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.services.CommonClass;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
    private TableView<Customers> tableView;

    @FXML
    private TableColumn<Customers, JFXButton> update;
    @FXML
    private TextField search;
    @FXML
    private Label usersCount;
    @FXML
    private Label outDatedCount;
    @FXML
    private Label activeCount;

    private FilteredList<Customers> filteredList;
    private SortedList<Customers> sortedList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            activeCount.setText(String.valueOf(paymentChecker.getActiveCustomers().size()));
            outDatedCount.setText(String.valueOf(paymentChecker.getOutdatedCustomers().size()));
            initTable();
            searchCustomer();


            for (Customers customer : paymentChecker.getAllCustomers()) {
                EventHandler<MouseEvent> updateHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

                    }
                };

                EventHandler<MouseEvent> information = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println(customer.getFirstName() + " info is touched...");
                    }
                };
                customer.getUpdate().addEventFilter(MouseEvent.MOUSE_CLICKED, updateHandler);
                customer.getInformation().addEventFilter(MouseEvent.MOUSE_CLICKED, updateHandler);
            }

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
                customers.getValue().getPayments().size() > 0 ? customers.getValue().getPayments().get(0).getPaymentDate() :
                        null));

        expDate.setCellValueFactory(customers -> new SimpleStringProperty(
                customers.getValue().getPayments().size() > 0 ?
                        customers.getValue().getPayments().get(0).getExpDate().toString() : null));

        informationBtn.setCellValueFactory(new PropertyValueFactory<>("information"));
        update.setCellValueFactory(new PropertyValueFactory<>("update"));
        tableView.setItems(paymentChecker.getAllCustomers());

    }


    private void searchCustomer() {
        filteredList = new FilteredList<>(paymentChecker.getAllCustomers(), b -> true);

        sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                if (customer.getFirstName().contains(newValue.toLowerCase()) || customer.getFirstName().contains(newValue.toUpperCase())) {
                    return true;
                } else if (customer.getPhone().contains(newValue)) {
                    return true;
                } else if (customer.getLastName().contains(newValue.toLowerCase()) || customer.getLastName().contains(newValue.toUpperCase())) {
                    return true;
                } else {
                    return false;
                }
            });
        });
    }
}
