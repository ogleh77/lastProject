package com.example.desktopapp.model;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.entity.Users;
import com.example.desktopapp.entity.serices.Box;
import com.example.desktopapp.exceptions.SqlCustomException;
import com.example.desktopapp.helpers.IConnection;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class CustomerDTO {

    public static int limit = 0;
    public static int id = 1;
    public static int numberOfCustomers = 0;
    public static Connection connection = IConnection.getConnection();

    //Insert customers
    public static void insertCustomer(Customers customer) throws SQLException {
        try {
            String insertQuery = "INSERT INTO customers(first_name, middle_name, last_name, phone, gander, shift, address, image, weight, who_added)\n" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        } catch (SQLException e) {
            if (e.getMessage().contains("(UNIQUE constraint failed: customers.phone)"))
                throw new SqlCustomException("Lanbarkan hore ayaa lo isticmalay fadlan hubi");
        }

    }


    //update customers
    public static void updateCustomer(Customers customer) throws SQLException {

        String updateQuery = "UPDATE customers SET first_name=?,middle_name=?,last_name=?,phone=?,gander=?,shift=?, " +
                "address=?,image=?,weight=? WHERE customer_id=" + customer.getCustomerId();

        PreparedStatement ps = connection.prepareStatement(updateQuery);

        ps.setString(1, customer.getFirstName());
        ps.setString(2, customer.getMiddleName());
        ps.setString(3, customer.getLastName());
        ps.setString(4, customer.getPhone());
        ps.setString(5, customer.getGander());
        ps.setString(6, customer.getShift());
        ps.setString(7, customer.getAddress());
        ps.setString(8, customer.getImage());
        ps.setDouble(9, customer.getWeight());

        ps.executeUpdate();
        ps.close();
        System.out.println("Customer updated");
    }

    //fetch all the customers with their payments

    public static ObservableList<Customers> fetchAllCustomer()
            throws SQLException {
        System.out.println("Limit is " + limit);

        ObservableList<Customers> customers = FXCollections.observableArrayList();

        //----------------------Pass the user's gander and role---------------------
        String fetchingAll = "SELECT * FROM customers";

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(fetchingAll);


        while (rs.next()) {
            numberOfCustomers++;
            // --------------Load phone of the customer-------------
            //  String customerPhone = rs.getString("phone");

            // --------------Fetch all the payments that has customer phone-------------
            //   ObservableList<Payments> payments = PaymentDTO.fetchPayments(customerPhone);
            Customers customer = new Customers(rs.getInt("customer_id"), rs.getString("first_name"),
                    rs.getString("middle_name"), rs.getString("last_name"),
                    rs.getString("phone"), rs.getString("gander"),
                    rs.getString("shift"), rs.getString("address"),
                    rs.getString("image"), rs.getDouble("weight"),
                    rs.getString("who_added"));

            customers.add(customer);
        }
        System.out.println("Number of customers " + numberOfCustomers);


        return customers;
    }

    public static ObservableList<Customers> fetchCustomersWithGenderWherePaymentIsOnline(Users activeUser)
            throws SQLException {
        //   System.out.println("Limit is " + limit);

        ObservableList<Customers> customers = FXCollections.observableArrayList();

        //----------------------Pass the user's gander and role---------------------
        String fetchingQueryWithGander = userSeparator(activeUser.getRole(), activeUser.getGender());

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(fetchingQueryWithGander);


        while (rs.next()) {
            limit++;
            // --------------Load phone of the customer-------------
            String customerPhone = rs.getString("phone");

            // --------------Fetch all the payments that has customer phone-------------
            ObservableList<Payments> payments = PaymentDTO.fetchPaymentsWhereIsOnline(customerPhone);
            Customers customer = new Customers(rs.getInt("customer_id"), rs.getString("first_name"),
                    rs.getString("middle_name"), rs.getString("last_name"),
                    rs.getString("phone"), rs.getString("gander"),
                    rs.getString("shift"), rs.getString("address"),
                    rs.getString("image"), rs.getDouble("weight"),
                    rs.getString("who_added"));

            customer.setPayments(payments);
            customers.add(customer);

        }
        System.out.println("Limit " + limit);

        return customers;
    }


    //------------------------helper methods-------------------------tested.....
    private static String userSeparator(String role, String gander) {
        String fetchQuery = "SELECT * FROM customers WHERE gander='" + gander +
                "'ORDER BY customer_id";

        if (role.equals("super_admin")) {
            System.out.println("Active customer is " + role);
            fetchQuery = "SELECT * FROM customers ORDER BY customer_id";
        }
        return fetchQuery;
    }


}
