package com.example.desktopapp.controllers;

import com.example.desktopapp.controllers.services.CustomerInfoController;
import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.helpers.CommonClass;
import com.example.desktopapp.helpers.PaymentChecker;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
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
            initTable();


            for (Customers customer : paymentChecker.getAllCustomers()) {
                System.out.println("Loaded customers..");
                EventHandler<MouseEvent> updateHandler = event -> {
                    try {
                        FXMLLoader loader = openWindow("/com/example/desktopapp/views/registrations.fxml", borderPane, null,
                                null, null);
                        RegistrationController controller = loader.getController();
                        controller.setBorderPane(borderPane);
                        controller.setCustomer(customer);
                        controller.setPaymentChecker(paymentChecker);
                    } catch (IOException ex) {
                        Alert alert = message(Alert.AlertType.ERROR, "Error: " + ex.getMessage(), "Error occurred");
                        alert.show();
                    }
                };

                EventHandler<MouseEvent> information = event -> {
                    try {
                        FXMLLoader loader = openWindow("/com/example/desktopapp/views/info/cutsomer-info.fxml", borderPane, null,
                                null, null);
                        CustomerInfoController controller = loader.getController();
                        controller.setBorderPane(borderPane);
                        controller.setCustomer(customer);
                        controller.setPaymentChecker(paymentChecker);
                    } catch (IOException ex) {
                        Alert alert = message(Alert.AlertType.ERROR, "Error: " + ex.getMessage(), "Error occurred");
                        alert.show();
                    }
                };

                customer.getInformation().addEventFilter(MouseEvent.MOUSE_CLICKED, information);
                customer.getUpdate().addEventFilter(MouseEvent.MOUSE_CLICKED, updateHandler);
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

        informationBtn.setCellValueFactory(new PropertyValueFactory<>("information"));
        update.setCellValueFactory(new PropertyValueFactory<>("update"));

        tableView.setItems(paymentChecker.getAllCustomers());
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
