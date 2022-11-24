package com.example.desktopapp.models;

import com.example.desktopapp.controllers.services.UserController;
import com.example.desktopapp.entities.Customers;
import com.example.desktopapp.entities.Payments;
import com.example.desktopapp.entities.services.Users;
import com.example.desktopapp.services.IConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;

public class CustomerDTO {
    public static int numberOfCustomers;
    public static int limit = 0;
    public static int id = 1;

    private static Connection connection = IConnection.getConnection();

    public static void insertCustomer(Customers customer) throws SQLException {
        String insertQuery = "INSERT INTO customers(first_name, middle_name, last_name, phone, gander, shift, address, image, weight, who_added)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(insertQuery);

        ps.setString(1, customer.getFirstName());
        ps.setString(2, customer.getMiddleName());
        ps.setString(3, customer.getLastName());
        ps.setString(4, customer.getPhone());
        ps.setString(5, customer.getGander());
        ps.setString(6, customer.getShift());
        ps.setString(7, customer.getAddress());
        ps.setString(8, customer.getImage());
        ps.setDouble(9, customer.getWeight());
        ps.setString(10, customer.getWhoAdded());

        ps.executeUpdate();
        ps.close();
        System.out.println("Customer added");
    }


    public static ObservableList<Customers> fetchCustomersWithGender(Users activeUser) throws SQLException {
        System.out.println("Limit is " + limit);

        ObservableList<Customers> customers = FXCollections.observableArrayList();
        String fetchQuery = "";
        if (activeUser.getRole().equals("SuperAdmin")) {
            System.out.println("Active customer is " + activeUser.getRole());
            fetchQuery = "SELECT * FROM payments LEFT JOIN customers on payments.customer_phone_fk = customers.phone WHERE is_online=true";

        } else {
            fetchQuery = "SELECT * FROM payments LEFT JOIN customers on payments.customer_phone_fk = customers.phone WHERE is_online=true AND gander='" + activeUser.getGender() + "'";
            System.out.println("Active customer is " + activeUser.getRole());
        }

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(fetchQuery);
        Customers customer;
        Payments payment;
        while (rs.next()) {
            limit++;
            customer = new Customers(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("middle_name"), rs.getString("last_name"),
                    rs.getString("phone"), rs.getString("gander"), rs.getString("shift"),
                    rs.getString("address"), rs.getString("image"), rs.getDouble("weight"),
                    rs.getString("who_added"));

            payment = new Payments(rs.getInt("payment_id"), rs.getString("payment_date"), LocalDate.parse(rs.getString("exp_date")), rs.getString("month"),
                    rs.getString("year"), rs.getDouble("amount_paid"), rs.getString("paid_by"),
                    rs.getDouble("discount"), rs.getBoolean("poxing"), rs.getString("customer_phone_fk"), rs.getBoolean("is_online"));

            if (customer.getPhone().equals(payment.getCustomerFK())) {
                customer.getPayments().add(payment);
            }

            customers.add(customer);

        }

        return customers;
    }


    public static ObservableList<Customers> fetchAllCustomersWithGender(Users activeUser) throws SQLException {
        System.out.println("Limit is " + limit);

        ObservableList<Customers> customers = FXCollections.observableArrayList();
        String fetchQuery = "";
        if (activeUser.getRole().equals("SuperAdmin")) {
            System.out.println("Active customer is " + activeUser.getRole());
            fetchQuery = "SELECT * FROM payments LEFT JOIN customers on payments.customer_phone_fk = customers.phone";

        } else {
            fetchQuery = "SELECT * FROM payments LEFT JOIN customers on payments.customer_phone_fk = customers.phone WHERE gander='" + activeUser.getGender() + "'";
            System.out.println("Active customer is " + activeUser.getRole());
        }

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(fetchQuery);
        Customers customer;
        Payments payment;
        while (rs.next()) {
            numberOfCustomers++;
            customer = new Customers(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("middle_name"), rs.getString("last_name"),
                    rs.getString("phone"), rs.getString("gander"), rs.getString("shift"),
                    rs.getString("address"), rs.getString("image"), rs.getDouble("weight"),
                    rs.getString("who_added"));

            payment = new Payments(rs.getInt("payment_id"), rs.getString("payment_date"), LocalDate.parse(rs.getString("exp_date")), rs.getString("month"),
                    rs.getString("year"), rs.getDouble("amount_paid"), rs.getString("paid_by"),
                    rs.getDouble("discount"), rs.getBoolean("poxing"), rs.getString("customer_phone_fk"), rs.getBoolean("is_online"));

            if (customer.getPhone().equals(payment.getCustomerFK())) {
                customer.getPayments().add(payment);
            }

            customers.add(customer);

        }

        return customers;
    }

}

