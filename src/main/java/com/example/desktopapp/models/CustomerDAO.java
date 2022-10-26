package com.example.desktopapp.models;

import com.example.desktopapp.entities.Box;
import com.example.desktopapp.entities.Customers;
import com.example.desktopapp.entities.Payments;
import com.example.desktopapp.genrals.Repo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.*;

public class CustomerDAO implements Repo<Customers> {
    Connection connection;

    public CustomerDAO() {
        connection = getConnection();
    }

    @Override
    public void insert(Customers customer) throws SQLException {
        String insertQuery = "INSERT INTO customers(first_name,middle_name,last_name, phone, gander, shift, address, image,weight,who_added) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?);";

        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

        preparedStatement.setString(1, customer.getFirstName());
        preparedStatement.setString(2, customer.getMiddleName());
        preparedStatement.setString(3, customer.getLastName());
        preparedStatement.setString(4, customer.getPhone());
        preparedStatement.setString(5, customer.getGander());
        preparedStatement.setString(6, customer.getShift());
        preparedStatement.setString(7, customer.getAddress());
        preparedStatement.setString(8, customer.getImage());
        preparedStatement.setDouble(9, customer.getWeight());
        preparedStatement.setString(10, customer.getWhoAdded());
        preparedStatement.executeUpdate();
        System.out.println("Added");

        preparedStatement.close();
    }

    @Override
    public void delete(Customers customers) throws SQLException {
        String deleteQuery = "DELETE FROM customers WHERE customer_id=" + customers.getCustomerId();

        Statement statement = connection.createStatement();

        statement.executeUpdate(deleteQuery);

        statement.close();
        System.out.println("customer deleted...");
    }

    @Override
    public void update(Customers customer) throws SQLException {
        String updateQuery = "UPDATE customers SET first_name=" + customer.getFirstName() + ",middle_name=" + customer.getMiddleName() + ",last_name=" + customer.getLastName() +
                ",phone=" + customer.getPhone() + ",gander=" + customer.getGander() + ",shift=" + customer.getShift() + ",address=" +
                customer.getAddress() + " image=" + customer.getImage() + "WHERE customer_id=" + customer.getCustomerId();

        Statement statement = connection.createStatement();

        statement.executeUpdate(updateQuery);

        System.out.println("Update");
        statement.close();
    }

    @Override
    public ObservableList<Customers> fetchAll() throws SQLException {
        ObservableList<Customers> customers = FXCollections.observableArrayList();

        ObservableList<Payments> payments = FXCollections.observableArrayList();
        String fetchQuery = "SELECT *\n" +
                "FROM payments\n" +
                "INNER JOIN customers ON payments.customer_fk = customers.customer_id\n" +
                "LEFT JOIN box ON payments.box_fk = box.box_id;";

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(fetchQuery);

        Customers customer;

        Payments payment;

        Box box;

        while (rs.next()) {
            customer = new Customers(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("middle_name"),
                    rs.getString("last_name"), rs.getString("phone"), rs.getString("gander"), rs.getString("shift"),
                    rs.getString("address"), rs.getString("image"), rs.getDouble("weight"), rs.getString("weight"));

            payment = new Payments(rs.getInt("payment_id"), rs.getString("payment_date"),
                    rs.getString("exp_date"), rs.getString("month"), rs.getString("year"),
                    rs.getDouble("amount_paid"), rs.getString("paid_by"), rs.getDouble("discount"),
                    rs.getBoolean("poxing"), rs.getString("comment"));

            if (rs.getString("box_fk") != null) {

                box = new Box(rs.getInt("box_id"), rs.getString("box_name"), rs.getBoolean("is_ready"));
                payment.setBox(box);
                payments.add(payment);
            }

            customer.setPayments(payments);
            customers.add(customer);

        }
        statement.close();
        rs.close();
        return customers;
    }

//    @Override
//    public Customers get(String key) throws SQLException {
//
//        Customers customer;
//        String findByKey = "SELECT *\n" +
//                "FROM payments\n" +
//                "INNER JOIN customers ON payments.customer_fk = customers.customer_id\n" +
//                "LEFT JOIN box ON payments.box_fk = box.box_id WHERE customer_id=" + key;
//
//        Statement statement = connection.createStatement();
//        ResultSet rs = statement.executeQuery(findByKey);
//
//
//        while (rs.next()) {
//            customer = new Customers(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("middle_name"),
//                    rs.getString("last_name"), rs.getString("phone"), rs.getString("gander"), rs.getString("shift"),
//                    rs.getString("address"), rs.getString("image"), rs.getDouble("weight"), rs.getString("weight"));
//
//            payment = new Payments(rs.getInt("payment_id"), rs.getString("payment_date"),
//                    rs.getString("exp_date"), rs.getString("month"), rs.getString("year"),
//                    rs.getDouble("amount_paid"), rs.getString("paid_by"), rs.getDouble("discount"),
//                    rs.getBoolean("poxing"), rs.getString("comment"));
//
//            if (rs.getString("box_fk") != null) {
//
//                box = new Box(rs.getInt("box_id"), rs.getString("box_name"), rs.getBoolean("is_ready"));
//                payment.setBox(box);
//                payments.add(payment);
//            }
//
//            customer.setPayments(payments);
//            customers.add(customer);
//
//        }
//        statement.close();
//        rs.close();
//        return customer;
//    }
};


