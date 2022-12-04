package com.example.desktopapp.models;

import com.example.desktopapp.entity.services.Box;
import com.example.desktopapp.entity.services.Gym;
import com.example.desktopapp.services.IConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class GymDTO {

    private static Connection connection = IConnection.getConnection();
    public static Gym currentGym;


    static {
        try {
            currentGym = getCurrentGym();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void update(Gym gym) throws SQLException {
        String updateQuery = "UPDATE gym SET gym_name=?,fitness_cost=?,poxing_cost=?,vipBox=?," +
                "max_discount=? WHERE gym_id=" + gym.getGymId();
        PreparedStatement ps = connection.prepareStatement(updateQuery);
        ps.setString(1, gym.getGymName());
        ps.setDouble(2, gym.getFitnessCost());
        ps.setDouble(3, gym.getPoxingCost());
        ps.setDouble(4, gym.getBoxCost());
        ps.setDouble(5, gym.getMaxDiscount());

        ps.executeUpdate();
        ps.close();
        currentGym = gym;


    }

    public static Gym getCurrentGym() throws SQLException {

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM gym");


        currentGym = new Gym(rs.getInt(1), rs.getString(2), rs.getDouble(3),
                rs.getDouble(4), rs.getDouble(5), rs.getDouble(6));

        currentGym.getVipBoxes().setAll(boxes());
        rs.close();
        return currentGym;


    }

    public static ObservableList<Box> boxes() throws SQLException {
        ObservableList<Box> boxes = FXCollections.observableArrayList();
        Box box;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM box");

        while (rs.next()) {
            box = new Box(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
            boxes.add(box);
        }
        rs.close();
        statement.close();
        return boxes;
    }
}
