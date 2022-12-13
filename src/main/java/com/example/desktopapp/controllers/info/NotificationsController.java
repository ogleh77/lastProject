package com.example.desktopapp.controllers.info;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.helpers.CommonClass;
import com.example.desktopapp.helpers.PaymentChecker;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NotificationsController extends CommonClass implements Initializable {

    @FXML
    private AnchorPane outDatedVBox;

    @FXML
    private AnchorPane warningVBox;
    @FXML
    private Pagination pagination;

    private ObservableList<Integer> numbers = FXCollections.observableArrayList();

    public NotificationsController() {
        numbers.addAll(1, 2, 3, 4, 5);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> {
            pagination.setPageFactory(this::createPage);
            pagination.setPageCount(numbers.size());
        });
    }


    private VBox createPage(int pageIndex) {
        VBox vBox = new VBox(5);

        vBox.setStyle("-fx-background-color: white;" +
                "-fx-pref-width: 555;" +
                "-fx-pref-height: 548;" +
                "-fx-effect: dropshadow(gaussian, rgba(221, 202, 202, 0.3), 10, 0.0, 0.0, 0.0)");

        try {

            for (Customers customer : paymentChecker.getOutDatedCustomers()) {

                FXMLLoader loader = openWindow("/com/example/desktopapp/views/info/outdated.fxml", borderPane, null, null, null);
                AnchorPane anchorPane = loader.load();
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
}
