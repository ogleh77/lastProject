package com.example.desktopapp.models;

import com.example.desktopapp.entities.Box;
import com.example.desktopapp.entities.Customers;
import com.example.desktopapp.entities.Payments;
import com.example.desktopapp.entities.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerDAO {
    static Connection connection = IConnection.getConnection();
    public static int limit = 0;

    public static void update(Payments payment) throws SQLException {
        String updateQuery = "UPDATE payments\n" +
                "SET active=false\n" +
                "WHERE payment_id =" + payment.getPaymentID();

        Statement statement = IConnection.getConnection().createStatement();

        statement.executeUpdate(updateQuery);

        System.out.println(payment.getPaymentID() + " is false");
    }


    public static ObservableList<Customers> fetchPaymentsWithGender(Users activeUser) throws SQLException {
        ObservableList<Customers> customers = FXCollections.observableArrayList();
        String fetchQuery = "";
        if (activeUser.getRole().equals("super admin")) {
            System.out.println("Active customer is " + activeUser.getRole());
            fetchQuery = "SELECT * FROM payments LEFT JOIN customers on payments.customer_fk = customers.customer_id WHERE active=true";

        } else {
            fetchQuery = "SELECT * FROM payments LEFT JOIN customers on payments.customer_fk = customers.customer_id WHERE active=true AND gender='" + activeUser.getGender() + "'";
            System.out.println("Active customer is " + activeUser.getRole());
        }

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(fetchQuery);
        Customers customer;
        Payments payment;
        while (rs.next()) {
            limit++;
            customer = new Customers(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("gender"), rs.getString("phone"));
            payment = new Payments(rs.getInt("payment_id"), rs.getString("payment_date"), LocalDate.parse(rs.getString("exp_date")), rs.getString("customer_fk"));
            if (customer.getCustomerId() == Integer.parseInt((payment.getCustomerFK()))) {
                customer.getPayments().add(payment);
            }

            customers.add(customer);
        }

        return customers;
    }

    public static ObservableList<Customers> fetchAll() throws SQLException {
        ObservableList<Customers> customers = FXCollections.observableArrayList();

        ObservableList<Payments> payments = FXCollections.observableArrayList();
        String fetchQuery = "SELECT * FROM payments LEFT JOIN customers on payments.customer_fk = customers.phone WHERE active=true";

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(fetchQuery);

        Customers customer;

        Payments payment;

        Box box;

        while (rs.next()) {
            //   customer = new Customers(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("phone"));
            payment = new Payments(rs.getInt("payment_id"), rs.getString("payment_date"), LocalDate.parse(rs.getString("exp_date")), rs.getString("customer_fk"));
            payments.add(payment);

            while (payment.getCustomerFK().equals("phone")) {
                //      customer.getPayments().add(payment);
            }
            //  customers.add(customer);
//            if (rs.getString("box_fk") != null) {
//
//                box = new Box(rs.getInt("box_id"), rs.getString("box_name"), rs.getBoolean("is_ready"));
//                payment.setBox(box);
//            }
            //  customer.setPayments(payments);
        }


        statement.close();
        rs.close();
        return customers;
    }

};


