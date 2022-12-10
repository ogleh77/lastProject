package com.example.desktopapp.helpers;

import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.DriverManager;

public interface IConnection {

    static Connection getConnection() {

        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:sqlite:src/database/database.db");
            Class.forName("org.sqlite.JDBC");

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
            //System.exit(0);
        }
        System.out.println("Opened database successfully");
        return con;
    }
}
