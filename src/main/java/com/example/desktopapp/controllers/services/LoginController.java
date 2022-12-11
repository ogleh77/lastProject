package com.example.desktopapp.controllers.services;

import com.example.desktopapp.entity.Users;
import com.example.desktopapp.helpers.CommonClass;
import com.example.desktopapp.model.UsersDTO;
import javafx.application.Platform;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            try {
                userCombo.setItems(UsersDTO.users());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    void loginHandler(ActionEvent event) throws IOException {
        Users activeUser = userCombo.getValue();

        if (password.getText().equals(activeUser.getPassword())) {

            Stage stage = (Stage) userCombo.getScene().getWindow();

            FXMLLoader loader = openNormalWindow("/com/example/desktopapp/views/services/splash-screen.fxml");
            Scene scene = new Scene(loader.load());
            SplashScreenController splashScreen = loader.getController();
            splashScreen.setActiveUsers(userCombo.getValue());

            Stage splashStage = new Stage();
            splashStage.setScene(scene);
            splashStage.initStyle(StageStyle.UNDECORATED);
            splashStage.show();


            stage.close();
        } else {
            messageAlert("Authentication failed",
                    "fadlan passworka aad gelisay wa khalad", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void exitHandler(MouseEvent event) {

    }


}
