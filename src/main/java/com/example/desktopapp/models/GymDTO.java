package com.example.desktopapp.models;

import com.example.desktopapp.entities.Box;
import com.example.desktopapp.entities.services.Gym;
import com.example.desktopapp.services.IConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GymDTO {
    private static Connection connection = IConnection.getConnection();

    private ObservableList<Box> boxes = FXCollections.observableArrayList();

    private static Gym currentGym;


    public static void updateGym(Gym gym) {

        currentGym = gym;
    }

    public static ObservableList<Box> fetchBoxes() throws SQLException {
        String queryBox = "SELECT * FROM box";
        ObservableList<Box> boxes = FXCollections.observableArrayList();

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(queryBox);

        while (rs.next()) {
            Box box = new Box(rs.getInt("box_id"),
                    rs.getString("box_name"),
                    rs.getBoolean("is_ready"));


            boxes.add(box);
        }

        rs.close();
        statement.close();

        return boxes;
    }


    public static Gym getCurrentGym() throws SQLException {
        String gymQuery = "SELECT * FROM gym;";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(gymQuery);


        while (rs.next()) {
            currentGym = new Gym(rs.getInt("gym_id"), rs.getString("gym_name"), rs.getDouble("fitness_cost"),
                    rs.getDouble("poxing_cost"), rs.getDouble("vipBox"),
                    rs.getDouble("max_discount"));

            currentGym.getVipBoxes().addAll(fetchBoxes());
        }

        statement.close();
        rs.close();
        return currentGym;
    }
}
