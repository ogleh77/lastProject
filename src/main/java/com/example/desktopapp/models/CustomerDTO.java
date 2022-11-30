package com.example.desktopapp.models;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.entity.services.Users;
import com.example.desktopapp.services.IConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class CustomerDTO {

    public static int numberOfPayments;
    public static int limit = 0;
    public static int id = 1;

    private static Connection connection = IConnection.getConnection();
    //  private static ObservableList<Payments> payments = FXCollections.observableArrayList();

    public static void insertCustomer(Customers customer) throws SQLException {
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
    }

    public static ObservableList<Customers> fetchCustomersWithGender(Users activeUser) throws SQLException {

        System.out.println("Limit is " + limit);

        ObservableList<Customers> customers = FXCollections.observableArrayList();
        String fetchQuery = "SELECT * FROM payments LEFT JOIN customers on payments.customer_id_fk = customers.customer_id WHERE is_online=true AND gander='" + activeUser.getGender() + "' ORDER BY customer_id";

        if (activeUser.getRole().equals("SuperAdmin")) {
            System.out.println("Active customer is " + activeUser.getRole());
            fetchQuery = "SELECT * FROM payments LEFT JOIN customers on payments.customer_id_fk = customers.customer_id WHERE is_online=true ORDER BY customer_id";
        }
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(fetchQuery);
        Customers customer;
        Payments payment;


        while (rs.next()) {
            limit++;
            customer = new Customers(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("middle_name"), rs.getString("last_name"), rs.getString("phone"), rs.getString("gander"), rs.getString("shift"), rs.getString("address"), rs.getString("image"), rs.getDouble("weight"), rs.getString("who_added"));

            payment = new Payments(rs.getInt("payment_id"), rs.getString("payment_date"), LocalDate.parse(rs.getString("exp_date")), rs.getString("month"), rs.getString("year"), rs.getDouble("amount_paid"), rs.getString("paid_by"), rs.getDouble("discount"), rs.getBoolean("poxing"), rs.getString("customer_id_fk"), rs.getBoolean("is_online"));


            if (String.valueOf(customer.getCustomerId()).equals(payment.getCustomerFK())) {
                // customer.getPayments().add(payment);
                customer.setPayment(payment);
            }

            customers.add(customer);

        }


        return customers;

    }


    public static ObservableList<Customers> fetchAllCustomersWithGender(Users activeUser) throws SQLException {
        //  System.out.println("number is " + numberOfCustomers);

        ObservableList<Customers> customers = FXCollections.observableArrayList();
        String fetchQuery = "SELECT * FROM customers  WHERE gander='" + activeUser.getGender() + "' ORDER BY customer_id";

        if (activeUser.getRole().equals("SuperAdmin")) {
            System.out.println("Active customer is " + activeUser.getRole());
            fetchQuery = "SELECT * FROM customers ORDER BY customer_id";
        }

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(fetchQuery);


        while (rs.next()) {
            //   numberOfCustomers++;
            Customers customer = new Customers(rs.getInt("customer_id"), rs.getString("first_name"), rs.getString("middle_name"), rs.getString("last_name"), rs.getString("phone"), rs.getString("gander"), rs.getString("shift"), rs.getString("address"), rs.getString("image"), rs.getDouble("weight"), rs.getString("who_added"));

            customers.add(customer);

        }


        return customers;

    }


    public static ObservableList<Customers> fetchAllPayments(Users activeUser) throws SQLException {

        ObservableList<Customers> customers = FXCollections.observableArrayList();
        String fetchCustomersQuery = "SELECT * FROM customers  WHERE gander='" + activeUser.getGender() + "' ORDER BY customer_id";


        if (activeUser.getRole().equals("SuperAdmin")) {
            System.out.println("Active customer is " + activeUser.getRole());
            fetchCustomersQuery = "SELECT * FROM customers ORDER BY customer_id";
        }

        Statement cStatement = connection.createStatement();

        Statement pStatement = connection.createStatement();

        ResultSet crs = cStatement.executeQuery(fetchCustomersQuery);


        ResultSet prs;

        while (crs.next()) {
            limit++;
            Customers customer = new Customers(crs.getInt("customer_id"), crs.getString("first_name"),
                    crs.getString("middle_name"), crs.getString("last_name"), crs.getString("phone"),
                    crs.getString("gander"), crs.getString("shift"), crs.getString("address"),
                    crs.getString("image"), crs.getDouble("weight"), crs.getString("who_added"));


            prs = pStatement.executeQuery("SELECT * FROM payments WHERE customer_phone_fk=" + customer.getCustomerId());

            while (prs.next()) {

                Payments payment = new Payments(prs.getInt("payment_id"), prs.getString("payment_date"), LocalDate.parse(prs.getString("exp_date")),
                        prs.getString("month"), prs.getString("year"), prs.getDouble("amount_paid"), prs.getString("paid_by"), prs.getDouble("discount"),
                        prs.getBoolean("poxing"), prs.getString("customer_phone_fk"), prs.getBoolean("is_online"));

                customer.getPayments().add(payment);
                if (payment.isOnline()) {
                    customer.setPayment(payment);
                }
            }

            customers.add(customer);
        }
        return customers;
    }

}
