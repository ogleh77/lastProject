package com.example.desktopapp.model;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.entity.Users;
import com.example.desktopapp.entity.serices.Box;
import com.example.desktopapp.exceptions.SqlCustomException;
import com.example.desktopapp.helpers.IConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class CustomerDTO {
    public static int numberOfCustomers;
    public static int limit = 0;
    public static int id = 1;

    public static Connection connection = IConnection.getConnection();

    //Insert customers
    public static void insertCustomer(Customers customer) throws SQLException {
        try {
            String insertQuery = "INSERT INTO customers(first_name, middle_name, last_name, phone, gander, shift, address, image, weight, who_added)\n" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        } catch (SQLException e) {
            if (e.getMessage().contains("(UNIQUE constraint failed: customers.phone)"))
                throw new SqlCustomException("Lanbarkan hore ayaa lo isticmalay fadlan hubi");
        }

    }


    //update customers
    public static void updateCustomer(Customers customer) throws SQLException {

        String updateQuery = "UPDATE customers SET first_name=?,middle_name=?,last_name=?,phone=?,gander=?,shift=?, " +
                "address=?,image=?,weight=? WHERE customer_id=" + customer.customerId();

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

    //fetch all the customers with their payments

    public static ObservableList<Customers> fetchCustomersWithGender(Users activeUser)
            throws SQLException {
        System.out.println("Limit is " + limit);

        ObservableList<Customers> customers = FXCollections.observableArrayList();

        //----------------------Pass the user's gander and role---------------------
        String fetchingQueryWithGander = userSeparator(activeUser.role(), activeUser.gender());

        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(fetchingQueryWithGander);


        while (rs.next()) {
            //--------------Load phone of the customer-------------
            //String customerPhone = rs.getString("phone");

            //--------------Fetch all the payments that has customer phone-------------
            // ObservableList<Payments> payments = fetchPayments(customerPhone);
            Customers customer = new Customers(rs.getInt("customer_id"), rs.getString("first_name"),
                    rs.getString("middle_name"), rs.getString("last_name"),
                    rs.getString("phone"), rs.getString("gander"),
                    rs.getString("shift"), rs.getString("address"),
                    rs.getString("image"), rs.getDouble("weight"),
                    rs.getString("who_added"), null, null, null);

            customers.add(customer);
        }


        return customers;
    }


    //------------------------helper methods-------------------------tested.....
    private static String userSeparator(String role, String gander) {
        String fetchQuery = "SELECT * FROM customers WHERE gander='" + gander +
                "'ORDER BY customer_id";

        if (role.equals("superAdmin")) {
            System.out.println("Active customer is " + role);
            fetchQuery = "SELECT * FROM customers ORDER BY customer_id";
        }
        return fetchQuery;
    }

    //Fetch payments according to customer that belongs
    public static ObservableList<Payments> fetchPayments(String phone) throws SQLException {
        ObservableList<Payments> payments = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();

        Payments payment = null;
        ResultSet rs = statement.executeQuery("SELECT * FROM payments LEFT JOIN box b on payments.box_fk = b.box_id " +
                "WHERE customer_phone_fk=" + phone + " ORDER BY exp_date DESC ");

        while (rs.next()) {
            Box box = null;
            if (rs.getString("box_fk") != null) {
                box = new Box(rs.getInt("box_id"), rs.getString("box_name"), rs.getBoolean("is_ready"));
            }

            payment = new Payments(rs.getInt("payment_id"), rs.getString("payment_date"),
                    LocalDate.parse(rs.getString("exp_date")), rs.getString("month"),
                    rs.getString("year"), rs.getDouble("amount_paid"),
                    rs.getString("paid_by"), rs.getDouble("discount"),
                    rs.getBoolean("poxing"), box, rs.getInt("customer_phone_fk"),
                    rs.getBoolean("is_online"), rs.getBoolean("pending"));
            payments.add(payment);
        }
        statement.close();
        rs.close();

        return payments;
    }


}
