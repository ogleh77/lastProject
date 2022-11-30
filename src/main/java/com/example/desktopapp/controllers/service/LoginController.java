package com.example.desktopapp.controllers.service;

import com.example.desktopapp.controllers.service.SplashScreenController;
import com.example.desktopapp.entity.services.Users;
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
        if (!password.getText().equals(userCombo.getValue().getPassword())) {
            messageAlert("Authentication failed", "Username and Password doesn't match", Alert.AlertType.ERROR);
        } else {

            //System.out.println("Valid..");
            stage = (Stage) userCombo.getScene().getWindow();
//
            FXMLLoader loader = openNormalWindow("/com/example/desktopapp/views/service/splash-screen.fxml");
            Scene scene = new Scene(loader.load());
            SplashScreenController splashScreen = loader.getController();
            splashScreen.setActiveUser(userCombo.getValue());

//
            Stage splashStage = new Stage();
            splashStage.setScene(scene);
            splashStage.initStyle(StageStyle.UNDECORATED);
            splashStage.show();


            stage.close();


//            for (Customers customer : CustomerDTO.fetchAllPayments(userCombo.getValue())) {
//                System.out.println(customer.getCustomerId() + " " + customer.getFirstName() + " Has " + customer.getPayments().size() + " gender " + customer.getGander());
//                if (customer.getPayment() == null) {
//                    System.out.println("It' not active ");
//                } else {
//                    System.out.println(customer.getCustomerId() + " name: " + customer.getFirstName() + " " + customer.getPayment());
//                }
//
//            }


//            //System.out.println(i);
//            System.out.println("Size of active " + activeCustomers);
//
//            System.out.println("====================================");
//
//
//            System.out.println("Size of outdated " + outdatedCustomers);
        }
    }

    @FXML
    void exitHandler(MouseEvent event) throws SQLException {


    }
}
