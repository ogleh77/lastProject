package com.example.desktopapp.models;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.entity.services.Users;
import com.example.desktopapp.services.IConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;

public class CustomerDTO {
    public static int limit = 0;
    public static int id = 1;

    private static Connection connection = IConnection.getConnection();

//    public static void insertCustomer(Customers customer) throws SQLException {
//        createCustomer(customer);
//        System.out.println("Customer added");
//    }


    public static void updateCustomer(Customers customer) throws SQLException {
        String updateQuery = "\n" +
                "UPDATE customers SET\n" +
                "first_name=?,middle_name=?,last_name=?,phone=?,gander=?,shift=?,address=?,\n" +
                "image=?,weight=? WHERE customer_id=" + customer.getCustomerId();

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
        System.out.println("Customer update " + customer.getCustomerId());
    }


    public static ObservableList<Customers> fetchAllPayments(Users activeUser) throws SQLException {
        ObservableList<Customers> customers = FXCollections.observableArrayList();
        String fetchCustomersQuery = "SELECT * FROM customers  WHERE gander='" + activeUser.getGender() +
                "'ORDER BY customer_id";

        if (activeUser.getRole().equals("SuperAdmin")) {
            System.out.println("Active customer is " + activeUser.getRole());
            fetchCustomersQuery = "SELECT * FROM customers ORDER BY customer_id";
        }

        Statement cStatement = connection.createStatement();

        ResultSet crs = cStatement.executeQuery(fetchCustomersQuery);

        Statement pStatement = connection.createStatement();

        ResultSet prs;
        while (crs.next()) {
            limit++;
            Customers customer = new Customers(crs.getInt("customer_id"), crs.getString("first_name"),
                    crs.getString("middle_name"), crs.getString("last_name"), crs.getString("phone"),
                    crs.getString("gander"), crs.getString("shift"), crs.getString("address"),
                    crs.getString("image"), crs.getDouble("weight"), crs.getString("who_added"));


            prs = pStatement.executeQuery("SELECT * FROM payments WHERE " +
                    "customer_phone_fk=" + customer.getPhone() + " ORDER BY exp_date DESC;");

            while (prs.next()) {

                Payments payment = new Payments(prs.getInt("payment_id"), prs.getString("payment_date"), LocalDate.parse(prs.getString("exp_date")),
                        prs.getString("month"), prs.getString("year"), prs.getDouble("amount_paid"), prs.getString("paid_by"), prs.getDouble("discount"),
                        prs.getBoolean("poxing"), prs.getString("customer_phone_fk"), prs.getBoolean("is_online"));

                customer.getPayments().add(payment);

            }
            customers.add(customer);


        }

        return customers;
    }


    //------------------Helper methods--------------------


    //------------------Transactional method
    public static void insertCustomerWithPayment(Customers customer) throws SQLException {
        connection.setAutoCommit(false);
        try {

            //-------------Insert customers----------------------
            createCustomer(customer);
            //-------------make customer's payment---------------
            makePayment(customer);


            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }


    }

    private static void createCustomer(Customers customer) throws SQLException {
        String insertCustomerQuery = "INSERT INTO customers(first_name, middle_name, last_name, phone, gander, " +
                "shift, address, image, weight, who_added)\n" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(insertCustomerQuery);

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
        System.out.println("Customer added");
    }

    public static void makePayment(Customers customer) throws SQLException {

        String insertPaymentQuery = "INSERT INTO payments(exp_date, amount_paid, paid_by," +
                "discount,poxing,box_fk, customer_phone_fk) VALUES (?,?,?,?,?,?,?)";

        PreparedStatement ps = connection.prepareStatement(insertPaymentQuery);

        ps.setString(1, customer.getPayments().get(0).getExpDate().toString());
        ps.setDouble(2, customer.getPayments().get(0).getAmountPaid());
        ps.setString(3, customer.getPayments().get(0).getPaidBy());
        ps.setDouble(4, customer.getPayments().get(0).getDiscount());
        ps.setBoolean(5, customer.getPayments().get(0).isPoxing());

        if (customer.getPayments().get(0).getBox() == null) {
            ps.setString(6, null);
        } else {
            ps.setInt(6, customer.getPayments().get(0).getBox().getBoxId());
        }

        ps.setString(7, customer.getPhone());
        ps.executeUpdate();

        //-------------make the payment's report-------------
        makeReport(customer, LocalDate.now());

        System.out.println("Payment inserted");
    }

    private static void makeReport(Customers customer, LocalDate today) throws SQLException {
        Statement st = connection.createStatement();

        if (customer.getGander().equals("Male") && customer.getPayments().get(0).getBox() != null) {
            DailyReportDTO.dailyReportMaleWithBox(today, st);

        } else if (customer.getGander().equals("Female") && customer.getPayments().get(0).getBox() != null) {
            DailyReportDTO.dailyReportFemaleWithBox(today, st);

        } else if (customer.getPayments().get(0).getBox() == null && customer.getGander().equals("Male")) {
            DailyReportDTO.dailyReportMaleWithOutBox(today, st);
        } else if (customer.getPayments().get(0).getBox() == null && customer.getGander().equals("Female")) {
            DailyReportDTO.dailyReportFemaleWithOutBox(today, st);
        }
        int arr[] = st.executeBatch();
        System.out.println(Arrays.toString(arr));
        st.close();
    }
}
