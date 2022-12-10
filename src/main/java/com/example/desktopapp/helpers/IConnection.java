package com.example.desktopapp.helpers;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class IConnection {

    static final Connection con;

    static {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:src/database/database.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static Connection getConnection() {

        try {
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
