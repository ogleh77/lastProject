package com.example.desktopapp.models;

import com.example.desktopapp.entity.DailyReport;
import com.example.desktopapp.services.IConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class DailyReportDTO {
    private static Connection connection = IConnection.getConnection();

//    private static String month;

    public ObservableList<DailyReport> getAll() throws SQLException {
        ObservableList<DailyReport> dailyReports = FXCollections.observableArrayList();
        DailyReport dailyReport = null;
        String sql = "SELECT * FROM daily_report";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            dailyReport = new DailyReport(
                    rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
                    rs.getInt(5), rs.getInt(6));
            dailyReports.add(dailyReport);
        }
        // connection.close();
        statement.close();
        rs.close();
        return dailyReports;
    }


    public static void dailyReportMaleWithBox(LocalDate date, Statement st) throws SQLException {
//        month = String.valueOf(date).substring(0, 7);
//        System.out.println("date is " + date);

        if (st.executeUpdate("UPDATE daily_report SET registration=(registration+1),male=(male+1),vip_box=(vip_box+1) " + "WHERE report_day ='" + date + "'") > 0) {
            //st.addBatch();
            System.out.println("Updated male with box....");

        } else {
            String query = "INSERT INTO daily_report(registration,male,vip_box)VALUES (1,1,1)";
            // st.executeUpdate("INSERT INTO daily_report(regstared,male,vip_box)VALUES (1,1,1)");

            st.addBatch(query);
            System.out.println("Saved male with box....");
            //    monthlyDao.monthlyReportMaleWithBox(month, st);
        }
    }


    public static void dailyReportFemaleWithBox(LocalDate date, Statement st) throws SQLException {

//        month = String.valueOf(date).substring(0, 7);

      //  System.out.println(month);
        if (st.executeUpdate("UPDATE daily_report SET registration=(registration+1),female=(female+1),vip_box=(vip_box+1) " + "WHERE report_day ='" + date + "'") > 0) {
            //st.addBatch();
            System.out.println("Updated Female with box....");

        } else {
            String query = "INSERT INTO daily_report(registration,female,vip_box)VALUES (1,1,1)";
            // st.executeUpdate("INSERT INTO daily_report(regstared,male,vip_box)VALUES (1,1,1)");
            st.addBatch(query);
            System.out.println("Saved Female with box....");

        }
    }


    public static void dailyReportMaleWithOutBox(LocalDate date, Statement st) throws SQLException {

//        month = String.valueOf(date).substring(0, 7);
//

      //  System.out.println("Month is " + month);
        if (st.executeUpdate("UPDATE daily_report SET registration=(registration+1),male=(male+1) WHERE report_day='" + date + "'") > 0) {
            //st.addBatch();
            System.out.println("Updated male with out box....");

        } else {
            String query = "INSERT INTO daily_report(registration,male)VALUES (1,1)";
            // st.executeUpdate("INSERT INTO daily_report(regstared,male,vip_box)VALUES (1,1,1)");
            st.executeUpdate(query);
            System.out.println("Saved male with out box....");

        }
    }


    public static void dailyReportFemaleWithOutBox(LocalDate date, Statement st) throws SQLException {
//        month = String.valueOf(date).substring(0, 7);

        if (st.executeUpdate("UPDATE daily_report SET registration=(registration+1),female=(female+1)" + "WHERE report_day ='" + date + "'") > 0) {
            //st.addBatch();
            System.out.println("Updated female with out box....");

        } else {
            String query = "INSERT INTO daily_report(registration,female)VALUES (1,1)";
            // st.executeUpdate("INSERT INTO daily_report(regstared,male,vip_box)VALUES (1,1,1)");
            st.addBatch(query);
            System.out.println("Saved female with out box....");

        }
    }

}
