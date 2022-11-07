package com.example.desktopapp.models;
import com.example.desktopapp.entities.services.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDTO  {

    static Connection connection = IConnection.getConnection();


    public static void insertUser(Users user) {

    }

    public static void deleteUser(Users user) {

    }

    public static void updateUser(Users user) {

    }

    public static ObservableList<Users> fetchUsers() throws SQLException {
        ObservableList<Users> users = FXCollections.observableArrayList();
        String fetchUsersQuery = "SELECT * FROM users";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(fetchUsersQuery);

        Users user;
        while (rs.next()) {
            user = new Users(rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6),
                    rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
            users.add(user);
        }
        users.add(new Users(0, "Jama", "Husein", "4303923", "Male", "Morning", "ogleh", "4000", null, "super admin"));

        statement.close();
        rs.close();
        return users;
    }
}
