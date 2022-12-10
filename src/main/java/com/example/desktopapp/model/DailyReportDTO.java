package com.example.desktopapp.model;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class DailyReportDTO {

    public static void dailyReportMaleWithBox(LocalDate date, Statement st) throws SQLException {
        //    month = String.valueOf(date).substring(0, 7);
        System.out.println("date is " + date);

        if (st.executeUpdate("UPDATE daily_report SET registration=(registration+1),male=(male+1),vip_box=(vip_box+1) " + "WHERE report_date ='" + date + "'") > 0) {
            //st.addBatch();
            System.out.println("Updated male with box....");

        } else {
            String query = "INSERT INTO daily_report(report_date,registration,male,vip_box)VALUES ('" + date + "',1,1,1)";
            // st.executeUpdate("INSERT INTO daily_report(regstared,male,vip_box)VALUES (1,1,1)");

            st.executeUpdate(query);

            System.out.println("Saved male with box....");
            //    monthlyDao.monthlyReportMaleWithBox(month, st);
        }
    }


    public static void dailyReportFemaleWithBox(LocalDate date, Statement st) throws SQLException {

        //     month = String.valueOf(date).substring(0, 7);

        // System.out.println(month);
        if (st.executeUpdate("UPDATE daily_report SET registration=(registration+1),female=(female+1),vip_box=(vip_box+1) " + "WHERE report_date ='" + date + "'") > 0) {
            //st.addBatch();

            System.out.println("Updated Female with box....");

        } else {
            String query = "INSERT INTO daily_report(registration,female,vip_box)VALUES (1,1,1)";
            // st.executeUpdate("INSERT INTO daily_report(regstared,male,vip_box)VALUES (1,1,1)");
//            st.addBatch(query);
            st.executeUpdate(query);
            System.out.println("Saved Female with box....");

        }
    }


    public static void dailyReportMaleWithOutBox(LocalDate date, Statement st) throws SQLException {

//        month = String.valueOf(date).substring(0, 7);
//
//
//        System.out.println("Month is " + month);
        if (st.executeUpdate("UPDATE daily_report SET registration=(registration+1),male=(male+1) WHERE report_date='" + date + "'") > 0) {
            //st.addBatch();
            System.out.println("Updated male with out box....");

        } else {
            String query = "INSERT INTO daily_report(registration,male)VALUES (1,1)";
            // st.executeUpdate("INSERT INTO daily_report(regstared,male,vip_box)VALUES (1,1,1)");
            // st.addBatch(query);
            st.executeUpdate(query);
            System.out.println("Saved male with out box....");

        }
    }


    public static void dailyReportFemaleWithOutBox(LocalDate date, Statement st) throws SQLException {
        //   date = String.valueOf(date).substring(0, 7);

        if (st.executeUpdate("UPDATE daily_report SET registration=(registration+1),female=(female+1)" + "WHERE report_date ='" + date + "'") > 0) {
            //st.addBatch();
            System.out.println("Updated female with out box....");

        } else {
            String query = "INSERT INTO daily_report(registration,female)VALUES (1,1)";
            st.executeUpdate(query);

            System.out.println("Saved female with out box....");

        }
    }
}
