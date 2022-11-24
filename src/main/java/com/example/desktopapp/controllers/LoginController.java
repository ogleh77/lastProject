package com.example.desktopapp.controllers;

import com.example.desktopapp.controllers.services.SplashScreenController;
import com.example.desktopapp.entities.services.Users;
import com.example.desktopapp.models.DailyReportDTO;
import com.example.desktopapp.models.UsersDTO;
import com.example.desktopapp.services.CommonClass;
import com.example.desktopapp.services.IConnection;
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
    void loginHandler(ActionEvent event) throws IOException {

        if (!password.getText().equals(userCombo.getValue().getPassword())) {
            Alert alert = messageAlert("Authentication failed", "Username OR Password incorrect", Alert.AlertType.ERROR);
            alert.showAndWait();
        } else {
            stage = (Stage) userCombo.getScene().getWindow();

            FXMLLoader loader = openNormalWindow("/com/example/desktopapp/views/service/splash-screen.fxml");
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
    void exitHandler(MouseEvent event) throws SQLException {

        try {
            Statement statement = IConnection.getConnection().createStatement();
            DailyReportDTO.dailyReportMaleWithOutBox(LocalDate.now(), statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
