package com.example.desktopapp.model;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.services.IConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerModel {

    public static int numberOfCustomers;
    public static int limit = 0;
    public static int id = 1;

    private static Connection connection = IConnection.getConnection();

    //--------------------Insert customers----------------
    public static void insertCustomer(Customers customer) throws SQLException {

        String insertQuery = "INSERT INTO customers(first_name, middle_name, last_name, phone, gander, shift, address, image, weight, who_added)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(insertQuery);

        ps.setString(1, customer.firstname());
        ps.setString(2, customer.middleName());
        ps.setString(3, customer.lastName());
        ps.setString(4, customer.phone());
        ps.setString(5, customer.gander());
        ps.setString(6, customer.shift());
        ps.setString(7, customer.address());
        ps.setString(8, customer.image());
        ps.setDouble(9, customer.weight());
        ps.setString(10, customer.whoAdded());

        ps.executeUpdate();
        ps.close();
        System.out.println("Customer added");
    }

    //--------------------update customers----------------
    public static void updateCustomer(Customers customer) throws SQLException {

        String updateQuery = "UPDATE customers SET first_name=?,middle_name=?,last_name=?,phone=?,gander=?,shift=?,\n" +
                "                     address=?,image=?,weight=? WHERE customer_id=" + customer.customerId();

        PreparedStatement ps = connection.prepareStatement(updateQuery);

        ps.setString(1, customer.firstname());
        ps.setString(2, customer.middleName());
        ps.setString(3, customer.lastName());
        ps.setString(4, customer.phone());
        ps.setString(5, customer.gander());
        ps.setString(6, customer.shift());
        ps.setString(7, customer.address());
        ps.setString(8, customer.image());
        ps.setDouble(9, customer.weight());

        ps.executeUpdate();
        ps.close();
        System.out.println("Customer updated");
    }


}
