package com.example.desktopapp.model;

import com.example.desktopapp.entity.Payments;
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
    private static final Connection connection = IConnection.getConnection();

    public static void pendPayment(Payments payment, int daysRemind) throws SQLException {
        connection.setAutoCommit(false);
        Statement statement = null;
        try {
            String pendQuery = "INSERT INTO pending(payment_fk,days_remain)" +
                    "VALUES (" + daysRemind + "," + payment.paymentId() + ")";

            String paymentOffQuery = "UPDATE payments SET is_online=false,pending=true WHERE payment_id=" + payment.paymentId();
            statement = connection.createStatement();

            if (payment.box() != null) {
                PaymentDTO.setTookBoxIsReadyTrue(payment.box());
            }

            statement.addBatch(pendQuery);
            statement.addBatch(paymentOffQuery);

            statement.executeBatch();


            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            statement.close();
        }
    }


    public static void updatePendingPayment(Pending pending) throws SQLException {
        connection.setAutoCommit(false);

        try {
            LocalDate remaindDate = LocalDate.now().plusDays(pending.daysRemain());
            String setPaymentOnlineTrue = "UPDATE payments SET is_online=true, pending=false, exp_date='" + remaindDate + "'" +
                    "WHERE payment_id=" + pending.payment().paymentId();


            String setPendingFalse = "UPDATE pending SET is_pending=false" +
                    " WHERE pending_id=" + pending.pendingId();

            Statement statement = connection.createStatement();

            statement.addBatch(setPendingFalse);
            statement.addBatch(setPaymentOnlineTrue);


            statement.executeBatch();

            connection.commit();
            System.out.println("Payment re online");
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }


    public static Pending fetchSinglePendingPayment(int pendingId) throws SQLException {
        String fetchPendingsQuery = "SELECT *\n" +
                "FROM pending LEFT JOIN payments p on pending.payment_fk = p.payment_id WHERE is_pending = true; AND pending_id=" + pendingId;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(fetchPendingsQuery);
        Pending pending = null;
        while (rs.next()) {
            int paymentID = rs.getInt("payment_fk");

            pending = new Pending(rs.getInt("pending_id"),
                    rs.getString("pending_date"),
                    rs.getInt("days_remain"), PaymentDTO.fetchSinglePayment(paymentID), rs.getBoolean("is_pending"));

        }
        rs.close();
        statement.close();


        return pending;
    }

    public static ObservableList<Pending> fetchPendingPayments() throws SQLException {
        ObservableList<Pending> pendings = FXCollections.observableArrayList();

        String fetchPendingsQuery = "SELECT *\n" +
                "FROM pending LEFT JOIN payments p on pending.payment_fk = p.payment_id WHERE is_pending = true;";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(fetchPendingsQuery);

        while (rs.next()) {
            int paymentID = rs.getInt("payment_fk");

            Pending pending = new Pending(rs.getInt("pending_id"),
                    rs.getString("pending_date"),
                    rs.getInt("days_remain"), PaymentDTO.fetchSinglePayment(paymentID), rs.getBoolean("is_pending"));

            pendings.add(pending);
        }
        rs.close();
        statement.close();

        return pendings;
    }


}
