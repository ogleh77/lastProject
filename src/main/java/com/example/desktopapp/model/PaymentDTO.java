package com.example.desktopapp.model;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.entity.Users;
import com.example.desktopapp.entity.serices.Box;
import com.example.desktopapp.helpers.IConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;

public class PaymentDTO {

    public static final Connection connection = IConnection.getConnection();
    public static int limit = 0;


    public static void insertPayment(String customerPhone, String customerGender, Payments payment) throws SQLException {
        connection.setAutoCommit(false);
        try {

            String insertPaymentQuery = "INSERT INTO payments(exp_date, amount_paid, paid_by," +
                    "discount,poxing,box_fk, customer_phone_fk) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(insertPaymentQuery);
            ps.setString(1, payment.expDate().toString());
            ps.setDouble(2, payment.amountPaid());
            ps.setString(3, payment.paidBy());
            ps.setDouble(4, payment.discount());
            ps.setBoolean(5, payment.poxing());

            if (payment.box() == null) {
                ps.setString(6, null);
            } else {

                ps.setInt(6, payment.box().boxId());

                //set the box isReady off if the payment took
                setTookBoxIsReadyFalse(payment.box());


            }

            ps.setString(7, customerPhone);
            ps.executeUpdate();

            System.out.println("Payment inserted");

            //-------------make the payment's report-------------
            makeReport(payment, customerGender);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }


    public static void insertCustomerWithPayment(Customers customer, Payments payment) throws SQLException {
        connection.setAutoCommit(false);
        try {
            CustomerDTO.insertCustomer(customer);


            insertPayment(customer.phone(), customer.gander(), payment);

            System.out.println("Job done");
        } catch (SQLException e) {
            throw e;
        }
    }


    public static void paymentOutDated(Payments payment) throws SQLException {
        connection.setAutoCommit(false);
        try {
            Statement statement = connection.createStatement();
            String setPaymentOff = "UPDATE payments SET is_online=false WHERE payment_id=" + payment.paymentId();


            if (payment.box() != null) {
                setTookBoxIsReadyTrue(payment.box());
            }

            statement.executeQuery(setPaymentOff);

            connection.commit();
            System.out.println("Payment offed " + payment);


        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    //-----------------------Fetch customer's payments---------------------
    public static ObservableList<Payments> fetchPayments(String phone) throws SQLException {

        //------------------------helper methods-------------------------tested.....

        //-------Fetch payments according to customer that belongs--------tested......
        ObservableList<Payments> payments = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();

        Payments payment = null;
        ResultSet rs = statement.executeQuery("SELECT * FROM payments LEFT JOIN box b on payments.box_fk = b.box_id " +
                "WHERE customer_phone_fk=" + phone + " ORDER BY exp_date DESC");

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

    public static ObservableList<Payments> fetchPaymentsWhereIsOnline(String customerPhone) throws SQLException {

        //------------------------helper methods-------------------------tested.....

        //-------Fetch payments according to customer that belongs--------tested......
        ObservableList<Payments> payments = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();

        Payments payment = null;
        ResultSet rs = statement.executeQuery("SELECT * FROM payments LEFT JOIN box b on payments.box_fk = b.box_id " +
                "WHERE customer_phone_fk=" + customerPhone + " AND is_online=true AND pending =false ORDER BY exp_date DESC ");

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


    public static Payments fetchSinglePayment(int paymentId) throws SQLException {
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


    //-------------------------make report------------------------------
    private static void makeReport(Payments payment, String customerGender) throws SQLException {
        Statement st = connection.createStatement();
        if (customerGender.equals("Male") && payment.box() != null) {
            DailyReportDTO.dailyReportMaleWithBox(st);
        } else if (customerGender.equals("Female") && payment.box() != null) {
            DailyReportDTO.dailyReportFemaleWithBox(st);
        } else if (payment.box() == null && customerGender.equals("Male")) {
            DailyReportDTO.dailyReportMaleWithOutBox(st);
        } else if (payment.box() == null && customerGender.equals("Female")) {
            DailyReportDTO.dailyReportFemaleWithOutBox(st);
        }
        int arr[] = st.executeBatch();
        System.out.println(Arrays.toString(arr));
        st.close();
    }


    private static void setTookBoxIsReadyFalse(Box box) throws SQLException {
        String boxFalseQuery = "UPDATE box SET is_ready=false WHERE box_id=" + box.boxId();
        Statement statement = connection.createStatement();
        statement.executeUpdate(boxFalseQuery);
        System.out.println(box.boxName() + " made false");
    }

    public static void setTookBoxIsReadyTrue(Box box) throws SQLException {
        String boxFalseQuery = "UPDATE box SET is_ready=true WHERE box_id=" + box.boxId();
        Statement statement = connection.createStatement();
        statement.executeUpdate(boxFalseQuery);
        System.out.println(box.boxName() + " made false");
    }

}
