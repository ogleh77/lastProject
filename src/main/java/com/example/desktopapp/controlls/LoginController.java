package com.example.desktopapp.controlls;

import com.example.desktopapp.Common;
import com.example.desktopapp.HelloApplication;
import com.example.desktopapp.entities.Users;
import com.example.desktopapp.models.CustomerDAO;
import com.example.desktopapp.models.UserDTO;
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

public class LoginController extends Common implements Initializable {
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
            userCombo.setItems(UserDTO.fetchUsers());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void loginHandler(ActionEvent event) throws IOException {

        if (!password.getText().equals(userCombo.getValue().getPassword())) {
            Alert alert = message(Alert.AlertType.ERROR, "Username OR Password incorrect", "Authentication failed");
            alert.showAndWait();
        } else {
            stage = (Stage) userCombo.getScene().getWindow();

            FXMLLoader loader = openNormalWindow("/com/example/desktopapp/views/splash-screen.fxml");
            Scene scene = new Scene(loader.load());
            SplashScreenController splashScreen = loader.getController();
            splashScreen.setActiveUser(userCombo.getValue());

            Stage splashStage = new Stage();
            splashStage.setScene(scene);
            splashStage.initStyle(StageStyle.UNDECORATED);
            splashStage.show();


            stage.close();

        }

    }

    @FXML
    void exitHandler(MouseEvent event) {
    }


}
