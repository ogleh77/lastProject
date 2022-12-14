package com.example.desktopapp.controllers.info;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.helpers.CommonClass;
import com.example.desktopapp.helpers.PaymentChecker;
import com.example.desktopapp.models.CustomerDTO;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OutDatedCustomerInfoController extends CommonClass implements Initializable {

    @FXML
    private JFXRadioButton female;

    @FXML
    private DatePicker fromDate;

    @FXML
    private JFXRadioButton male;

    @FXML
    private Pagination pagination;

    @FXML
    private ComboBox<String> shift;

    @FXML
    private DatePicker toDate;
    private ObservableList<Customers> fetchOutDatedCustomers = FXCollections.observableArrayList();
    private Stage loadingStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            female.setToggleGroup(genderGroup);
            male.setToggleGroup(genderGroup);
            shift.setItems(super.shift);
            pagination.setVisible(false);
            fetchSpecifiedPayments.setOnSucceeded(e -> {
                pagination.setVisible(true);
                pagination.setPageFactory(this::createPage);
                pagination.setPageCount(fetchOutDatedCustomers.size());
                loadingStage.close();
            });
        });
    }


    @FXML
    void searchHandler(ActionEvent event) throws SQLException, IOException {

        Thread thread = new Thread(fetchSpecifiedPayments);
        thread.setDaemon(true);
        thread.start();

        loadingStage = loadingStage("/com/example/desktopapp/views/services/loading-window.fxml");


    }

    public Task<Void> fetchSpecifiedPayments = new Task<>() {
        @Override
        protected Void call() throws Exception {
            fetchOutDatedCustomers = CustomerDTO.fetchCustomersWithGenderWherePaymentIsOfflineANDExpDateBtw(customerQueryQualification(),
                    fromDate.getValue().toString(), toDate.getValue().toString());
            return null;
        }
    };


    private String customerQueryQualification() {

        if (genderGroup.getSelectedToggle() != null && shift.getValue() != null) {
            String ganderSelected = male.isSelected() ? "Male" : "Female";
            return "SELECT * FROM customers WHERE shift='" + shift.getValue() + "' AND gander='" + ganderSelected + "'";
        } else if (genderGroup.getSelectedToggle() != null) {
            String ganderSelected = male.isSelected() ? "Male" : "Female";
            return "SELECT * FROM customers WHERE gander='" + ganderSelected + "'";
        } else if (shift.getValue() != null) {
            return "SELECT * FROM customers WHERE shift='" + shift.getValue() + "'";
        }

        return "SELECT * FROM customers";
    }

    private VBox createPage(int pageIndex) {
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setPrefHeight(519);
        vBox.setPrefWidth(567);

        FXMLLoader loader;
        AnchorPane anchorPane;
        try {

            for (Customers customer : fetchOutDatedCustomers) {
                loader = openNotificationWindow("/com/example/desktopapp/views/info/outdated.fxml");
                anchorPane = loader.load();
                OutDatedController controller = loader.getController();
                controller.setBorderPane(borderPane);
                controller.setPaymentChecker(paymentChecker);
                controller.setCustomer(customer);
                vBox.getChildren().add(anchorPane);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return vBox;
    }


    @Override
    public void setPaymentChecker(PaymentChecker paymentChecker) {
        super.setPaymentChecker(paymentChecker);
    }

    @Override
    public void setBorderPane(BorderPane borderPane) {
        super.setBorderPane(borderPane);
    }
}
