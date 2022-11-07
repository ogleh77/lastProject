package com.example.desktopapp.models;


import com.example.desktopapp.entities.Box;
import com.example.desktopapp.entities.services.Gym;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class GymDAO {
    private static Gym currentGym;
    private static Connection connection = IConnection.getConnection();

    public void insertBox(Box box) throws SQLException {

//        String insertBox = " INSERT INTO box(box_title,  is_ready) " +
//                "VALUES ('" + box.getBoxTitle() + "'," + true + ")";
//        Statement statement = connection.createStatement();
//
//        statement.executeUpdate(insertBox);
//
//        currentGym.getVipBoxes().add(box);
//        System.out.println("Box saved....");
//
//
//        statement.close();


    }

    //-------------------make available again if the box out date----------------------------
    public void outDatedBoxUpdate(Box box) throws SQLException {
        //catch and throw insert side when box updating
        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE box SET box_isready=" + true + " WHERE box_id=" + box.getBoxId());
        statement.close();
        System.out.println("Box " + box.getBoxName() + " updated and set " + box.isReady());

    }

    //-------------------make unavailable again if the box don't took before----------------------------
    public void updateTookBox(Box box) throws SQLException {
        //catch and throw insert side when box updating
        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE box SET box_status=" + false + " WHERE box_id=" + box.getBoxId());
        //   connection.close();
        statement.close();
        System.out.println("Box " + box.getBoxName() + " updated and set " + box.isReady());

    }


    public void update(Gym gym) throws SQLException {
        String updateQuery = "UPDATE gym SET gym_name=?,fitness_cost=?,poxing_cost=?,box_cost=?," +
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

        currentGym.getVipBoxes().addAll(getAll());
        rs.close();
        return currentGym;


    }

    public static ObservableList<Box> getAll() throws SQLException {
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
