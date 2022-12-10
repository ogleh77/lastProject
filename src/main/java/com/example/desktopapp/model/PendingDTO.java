package com.example.desktopapp.model;

import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.entity.serices.Box;
import com.example.desktopapp.entity.serices.Pending;
import com.example.desktopapp.helpers.IConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class PendingDTO {
    private static Connection connection = IConnection.getConnection();

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


    public static void insertPending(Payments payment) throws SQLException {
        LocalDate pendingDate = LocalDate.now();
        LocalDate expDate = payment.expDate();

        int daysRemain = (expDate.getDayOfMonth() - pendingDate.getDayOfMonth());

        String insertPending = "INSERT INTO pending(days_remain, payment_fk)" +
                " VALUES (" + daysRemain + "," + payment.paymentId() + ");";

        Statement statement = connection.createStatement();

        statement.executeUpdate(insertPending);

        System.out.println("payment " + payment.paymentId() + " Was pend");
    }


    public static ObservableList<Pending> fetchPending() throws SQLException {

        ObservableList<Pending> pendings = FXCollections.observableArrayList();
        Statement statement = connection.createStatement();


        ResultSet rs = statement.executeQuery("SELECT * FROM pending " +
                "LEFT JOIN payments p on pending.payment_fk = p.payment_id\n" +
                ";");

        while (rs.next()) {
            Payments payment = new Payments(rs.getInt("payment_id"), rs.getString("payment_date"),
                    LocalDate.parse(rs.getString("exp_date")), rs.getString("month"),
                    rs.getString("year"), rs.getDouble("amount_paid"),
                    rs.getString("paid_by"), rs.getDouble("discount"),
                    rs.getBoolean("poxing"), null, rs.getInt("customer_phone_fk"),
                    rs.getBoolean("is_online"), rs.getBoolean("pending"));

            Pending pending = new Pending(rs.getInt("pending_id"), rs.getString("pending_date"),
                    rs.getInt("days_remain"), payment, rs.getBoolean("is_pending"));

            pendings.add(pending);

        }
        return pendings;
    }


    public static Pending rePending(int paymentId) throws SQLException {

        Statement statement = connection.createStatement();


        ResultSet rs = statement.executeQuery("SELECT *\n" +
                "FROM pending\n" +
                "         LEFT JOIN payments p on pending.payment_fk = p.payment_id\n" +
                "WHERE payment_id =" + paymentId +
                "  AND is_pending = true;");

        Pending pending = null;
        while (rs.next()) {
            Payments payment = new Payments(rs.getInt("payment_id"), rs.getString("payment_date"),
                    LocalDate.parse(rs.getString("exp_date")), rs.getString("month"),
                    rs.getString("year"), rs.getDouble("amount_paid"),
                    rs.getString("paid_by"), rs.getDouble("discount"),
                    rs.getBoolean("poxing"), null, rs.getInt("customer_phone_fk"),
                    rs.getBoolean("is_online"), rs.getBoolean("pending"));

            pending = new Pending(rs.getInt("pending_id"), rs.getString("pending_date"),
                    rs.getInt("days_remain"), payment, rs.getBoolean("is_pending"));


        }

        return pending;
    }

}
