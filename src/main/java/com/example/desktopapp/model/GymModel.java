package com.example.desktopapp.model;

import com.example.desktopapp.entity.serices.Box;
import com.example.desktopapp.entity.serices.Gym;
import com.example.desktopapp.services.IConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GymModel {

    private static Connection connection = IConnection.getConnection();


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

        Gym currentGym = null;
        while (rs.next()) {
            currentGym = new Gym(rs.getInt("gym_id"), rs.getString("gym_name"), rs.getDouble("fitness_cost"),
                    rs.getDouble("poxing_cost"), rs.getDouble("vipBox"),
                    rs.getDouble("max_discount"), "43039", "33333", fetchBoxes());
        }

        statement.close();
        rs.close();
        return currentGym;
    }


    public static void boxTook(Box box) throws SQLException {
        box = new Box(box.boxId(), box.boxName(), false);
        String boxQuery = "UPDATE box SET is_ready=false WHERE box_id=" + box.boxId();

        Statement statement = connection.createStatement();

        statement.executeUpdate(boxQuery);
        System.out.println("Box " + box.boxName() + " made off");
        statement.close();
    }

    public static void boxReturned(Box box) throws SQLException {
        //    box = new Box(box.boxId(), box.boxName(), false);
        String boxQuery = "UPDATE box SET is_ready=true WHERE box_id=" + box.boxId();

        Statement statement = connection.createStatement();

        statement.executeUpdate(boxQuery);
        System.out.println("Box " + box.boxName() + " made on");
        statement.close();
    }

}
