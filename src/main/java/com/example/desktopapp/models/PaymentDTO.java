package com.example.desktopapp.models;

import com.example.desktopapp.entities.Customers;
import com.example.desktopapp.services.IConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;

public class PaymentDTO {

    private static Connection connection = IConnection.getConnection();

    public static void insertPayment(Customers customer) throws SQLException {
        connection.setAutoCommit(false);
        PreparedStatement ps = null;
        try {

            String insertQuery = "INSERT INTO payments(exp_date, amount_paid, paid_by," +
                    "discount,poxing,box_fk, customer_phone_fk) VALUES (?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(insertQuery);

            ps.setString(1, customer.getPayment().getExpDate().toString());
            ps.setDouble(2, customer.getPayment().getAmountPaid());
            ps.setString(3, customer.getPayment().getPaidBy());
            ps.setDouble(4, customer.getPayment().getDiscount());
            ps.setBoolean(5, customer.getPayment().isPoxing());

            if (customer.getPayment().getBox() == null) {
                ps.setString(6, null);
            } else {
                ps.setInt(6, customer.getPayment().getBox().getBoxId());
            }

            ps.setString(7, customer.getPhone());
            ps.executeUpdate();
            System.out.println("Payment inserted..");
            //System.out.println(1 / 0);
            qualification(customer, LocalDate.now());

            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {

            ps.close();

        }

    }


    public static void updatePayment(Customers customer) throws SQLException {
       // connection.setAutoCommit(false);
        PreparedStatement ps = null;
        try {

            String updateQuery = "UPDATE payments\n" +
                    "SET amount_paid=?,paid_by=?,discount=?,poxing=?,box_fk=? WHERE customer_phone_fk=" + customer.getPhone();

            ps = connection.prepareStatement(updateQuery);


            ps.setDouble(1, customer.getPayment().getAmountPaid());
            ps.setString(2, customer.getPayment().getPaidBy());
            ps.setDouble(3, customer.getPayment().getDiscount());
            ps.setBoolean(4, customer.getPayment().isPoxing());

            if (customer.getPayment().getBox() == null) {
                ps.setString(5, null);
            } else {
                ps.setInt(5, customer.getPayment().getBox().getBoxId());
            }

            ps.executeUpdate();
            System.out.println("Payment UPDATE.." + customer.getPayment());
            //System.out.println(1 / 0);
        //    qualification(customer, LocalDate.now());

       //     connection.commit();

        } catch (SQLException e) {
           // connection.rollback();

            //Throw this error
            e.printStackTrace();
        } finally {

            ps.close();

        }

    }

    //Qualify customer by their vip box and gander to make daily report registration.
    private static void qualification(Customers customer, LocalDate today) throws SQLException {
        Statement st = connection.createStatement();

        if (customer.getGander().equals("Male") && customer.getPayment().getBox() != null) {
            DailyReportDTO.dailyReportMaleWithBox(today, st);

        } else if (customer.getGander().equals("Female") && customer.getPayment().getBox() != null) {
            DailyReportDTO.dailyReportFemaleWithBox(today, st);

        } else if (customer.getPayment().getBox() == null && customer.getGander().equals("Male")) {
            DailyReportDTO.dailyReportMaleWithOutBox(today, st);
        } else if (customer.getPayment().getBox() == null && customer.getGander().equals("Female")) {
            DailyReportDTO.dailyReportFemaleWithOutBox(today, st);
        }
        int arr[] = st.executeBatch();
        System.out.println(Arrays.toString(arr));
        st.close();
    }
}
