package com.example.desktopapp.controllers;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.entity.services.Users;
import com.example.desktopapp.models.CostumerDTO;
import com.example.desktopapp.models.UsersDTO;
import com.example.desktopapp.services.CommonClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class LoginController extends CommonClass implements Initializable {
    @FXML
    private AnchorPane loginPane;

    @FXML
    private PasswordField password;

    @FXML
    private ComboBox<Users> userCombo;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            userCombo.setItems(UsersDTO.users());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void loginHandler(ActionEvent event) throws IOException, SQLException {
//        if (!password.getText().equals(userCombo.getValue().getPassword())) {
//            messageAlert("Authentication failed", "Username and Password doesn't match", Alert.AlertType.ERROR);
//        } else {
//
//            System.out.println("Valid..");
//            stage = (Stage) userCombo.getScene().getWindow();
////
//            FXMLLoader loader = openNormalWindow("/com/example/graadproject/views/views/services/splash-screen.fxml");
//            Scene scene = new Scene(loader.load());
//            SplashScreenController splashScreen = loader.getController();
//            splashScreen.setActiveUser(userCombo.getValue());
////
//            Stage splashStage = new Stage();
//            splashStage.setScene(scene);
//            splashStage.initStyle(StageStyle.UNDECORATED);
//            splashStage.show();
////
////
////            stage.close();


        // System.out.println(CostumerDTO.fetchAllCustomersWithGender(userCombo.getValue()).get(0));
        System.out.println("Size of customers " + CostumerDTO.fetchAllCustomersWithGender(userCombo.getValue()).size());
        /// System.out.println("size of payments "+CostumerDTO.getPayments().size());
//        for (Payments payments:CostumerDTO.fetchAllCustomersWithGender(userCombo.getValue()).get(0).getPayments()) {
//           // System.out.println(payments.getPaymentID()+" "+payments.getExpDate());
//        }


        for (Customers customer : CostumerDTO.fetchAllPayments(userCombo.getValue())) {
            System.out.println(customer.getCustomerId() + " " + customer.getFirstName() + " Has " + customer.getPayments().size());
        }


    }


    @FXML
    void exitHandler(MouseEvent event) throws SQLException {


    }
}
