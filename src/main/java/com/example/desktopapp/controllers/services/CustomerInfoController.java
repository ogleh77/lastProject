package com.example.desktopapp.controllers.services;

import com.example.desktopapp.HelloApplication;
import com.example.desktopapp.controllers.RegistrationController;
import com.example.desktopapp.controllers.info.PendingController;
import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.helpers.CommonClass;
import com.example.desktopapp.model.PaymentDTO;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CustomerInfoController extends CommonClass implements Initializable {
    @FXML
    private TableColumn<Payments, Double> amountPaid;

    @FXML
    private TableColumn<Payments, Double> discount;

    @FXML
    private TableColumn<Payments, LocalDate> expDate;

    @FXML
    private TableColumn<Payments, String> month;

    @FXML
    private TableColumn<Payments, String> paidBy;

    @FXML
    private TableColumn<Payments, String> paymentDate;

    @FXML
    private TableColumn<Payments, JFXButton> pendingBtn;

    @FXML
    private TableColumn<Payments, String> poxing;

    @FXML
    private TableColumn<Payments, String> running;

    @FXML
    private TableView<Payments> tableView;

    @FXML
    private TableColumn<Payments, String> vipBox;

    @FXML
    private TableColumn<Payments, String> year;


    @FXML
    private ImageView imgView;

    @FXML
    private Label fullName;

    @FXML
    private Label address;

    @FXML
    private Label phone;

    @FXML
    private Label shift;

    @FXML
    private Label weight;

    @FXML
    private Label gander;


    @FXML
    private Label whoAdded;


    private ObservableList<Payments> payments;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            initTable();

            for (Payments payment : payments) {

                EventHandler<MouseEvent> pendHandler = event -> {
                    try {
                        pendingStage(payment);
                    } catch (IOException ex) {
                        Alert alert = message(Alert.AlertType.ERROR, "Error: " + ex.getMessage(), "Error occurred");
                        alert.show();
                        ex.printStackTrace();
                    }
                };
                payment.getPendingBtn().addEventFilter(MouseEvent.MOUSE_CLICKED, pendHandler);
            }
        });


    }


    private void initTable() {
        amountPaid.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));

        discount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        expDate.setCellValueFactory(new PropertyValueFactory<>("expDate"));

        month.setCellValueFactory(new PropertyValueFactory<>("month"));

        paidBy.setCellValueFactory(new PropertyValueFactory<>("paidBy"));

        paymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));

        pendingBtn.setCellValueFactory(new PropertyValueFactory<>("pendingBtn"));

        poxing.setCellValueFactory(payment ->
                new SimpleStringProperty(payment.getValue().isPoxing() ? "Yes" : "No"));

        running.setCellValueFactory(payment ->
                new SimpleStringProperty(payment.getValue().isOnline() ? "Yes" : "No"));

        vipBox.setCellValueFactory(payment ->
                new SimpleStringProperty(payment.getValue().getBox() != null ? "Yes" : "No"));

        year.setCellValueFactory(new PropertyValueFactory<>("year"));

        tableView.setItems(payments);

    }

    @Override
    public void setCustomer(Customers customer) {
        super.setCustomer(customer);

        fullName.setText(customer.getFirstName() + " " + customer.getMiddleName() + " " + customer.getLastName());
        phone.setText(customer.getPhone());
        gander.setText(customer.getGander());
        address.setText(customer.getAddress() == null ? " no address " : customer.getAddress());
        shift.setText(customer.getShift());
        weight.setText(customer.getWeight() + "");
        whoAdded.setText(customer.getWhoAdded());


        try {
            payments = FXCollections.observableArrayList(PaymentDTO.fetchPayments(customer.getPhone()));
        } catch (SQLException e) {
            messageAlert("Error occur", "error " + e.getMessage(), Alert.AlertType.ERROR);
        }

        System.out.println(payments);

    }

    private Stage pendingStage(Payments payment) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/desktopapp/views/info/pending-confirm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        PendingController controller = fxmlLoader.getController();
        controller.setPayment(payment);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        return stage;
    }
}

