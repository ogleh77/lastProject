package com.example.desktopapp.genrals;

import java.sql.Connection;
import java.sql.DriverManager;

public interface IConnection {
    default Connection getConnection() {

        Connection con = null;

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:src/database/database.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return con;
    }
}
