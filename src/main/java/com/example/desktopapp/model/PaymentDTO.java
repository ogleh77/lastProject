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
import java.util.Arrays;

public class PaymentDTO {
    private static Connection connection = IConnection.getConnection();

    public static int numberOfCustomers;
    public static int limit = 0;

    //---------------------Create new payment-------------––

    public static void insertPayment(Customers customer) throws SQLException {
        String insertPaymentQuery = "INSERT INTO payments(exp_date, amount_paid, paid_by," +
                "discount,poxing,box_fk, customer_phone_fk) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(insertPaymentQuery);
        ps.setString(1, customer.payments().get(0).expDate().toString());
        ps.setDouble(2, customer.payments().get(0).amountPaid());
        ps.setString(3, customer.payments().get(0).paidBy());
        ps.setDouble(4, customer.payments().get(0).discount());
        ps.setBoolean(5, customer.payments().get(0).poxing());
        if (customer.payments().get(0).box() == null) {
            ps.setString(6, null);
        } else {
            ps.setInt(6, customer.payments().get(0).box().boxId());
        }

        ps.setString(7, customer.phone());
        ps.executeUpdate();

        //-------------make the payment's report-------------
        makeReport(customer, LocalDate.now());

        System.out.println("Payment inserted");
    }


//--------------------Insert customer with payment transactionaly----------------------------

    public static void insertCustomerWithPayment(Customers customer) throws SQLException {

        //step 1  set auto commit off
        connection.setAutoCommit(false);
        try {
            //setp 2  create customer
            insertCustomer(customer);
            //step 3 create payment
            PaymentDTO.insertPayment(customer);
            //  System.out.println(1 / 0);
            //Finally commit the opertions
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
//            throw e;
        }


    }
    //--------------------fetchAll customers according to it's gander--------------

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
            String customerPhone = rs.getString("phone");

            //--------------Fetch all the payments that has customer phone-------------
            ObservableList<Payments> payments = fetchPaymentsWhereIsOnline(customerPhone);


            Customers customer = new Customers(rs.getInt("customer_id"), rs.getString("first_name"),
                    rs.getString("middle_name"), rs.getString("last_name"),
                    rs.getString("phone"), rs.getString("gander"),
                    rs.getString("shift"), rs.getString("address"),
                    rs.getString("image"), rs.getDouble("weight"),
                    rs.getString("who_added"), null, null, payments);

            customers.add(customer);
        }


        return customers;
    }

    public static ObservableList<Payments> fetchPaymentsWhereIsOnline(String phone) throws SQLException {

        //------------------------helper methods-------------------------tested.....

        //-------Fetch payments according to customer that belongs--------tested......
        ObservableList<Payments> payments = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();

        Payments payment = null;
        ResultSet rs = statement.executeQuery("SELECT * FROM payments LEFT JOIN box b on payments.box_fk = b.box_id " +
                "WHERE customer_phone_fk=" + phone + " AND is_oline=true ORDER BY exp_date DESC ");

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

    private static String userSeparator(String role, String gander) {
        String fetchQuery = "SELECT * FROM customers WHERE gander='" + gander +
                "'ORDER BY customer_id";

        if (role.equals("superAdmin")) {
            System.out.println("Active customer is " + role);
            fetchQuery = "SELECT * FROM customers ORDER BY customer_id";
        }
        return fetchQuery;
    }

    //-------------------------make report------------------------------
    private static void makeReport(Customers customer, LocalDate today) throws SQLException {
        Statement st = connection.createStatement();
        if (customer.gander().equals("Male") && customer.payments().get(0).box() != null) {
            DailyReportDTO.dailyReportMaleWithBox(today, st);
        } else if (customer.gander().equals("Female") && customer.payments().get(0).box() != null) {
            DailyReportDTO.dailyReportFemaleWithBox(today, st);
        } else if (customer.payments().get(0).box() == null && customer.gander().equals("Male")) {
            DailyReportDTO.dailyReportMaleWithOutBox(today, st);
        } else if (customer.payments().get(0).box() == null && customer.gander().equals("Female")) {
            DailyReportDTO.dailyReportFemaleWithOutBox(today, st);
        }
        int arr[] = st.executeBatch();
        System.out.println(Arrays.toString(arr));
        st.close();
    }

    private static void insertCustomer(Customers customer) throws SQLException {
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

    //---------------make box not ready during in use ---------------------
    public static Payments payment(int paymentId) throws SQLException {
        Statement statement = connection.createStatement();

        Payments payment = null;
        ResultSet rs = statement.executeQuery("SELECT * FROM payments LEFT JOIN box b on payments.box_fk = b.box_id " +
                "WHERE payment_id=" + paymentId);

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
        }
        return payment;
    }

}
