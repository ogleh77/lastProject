package com.example.desktopapp.model;

import com.example.desktopapp.services.IConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DailyReportDTOTest {

    private Connection connection;

    Statement statement;



    @Test
    void dailyReportMaleWithBox() throws SQLException {
        LocalDate today = LocalDate.now();

        LocalDate tomorrow = LocalDate.now().plusDays(20);

        DailyReportDTO.dailyReportMaleWithBox(tomorrow, statement);


    }

    @Test
    void itShouldUploadDate() throws SQLException {
        System.out.println(DailyReportDTO.fetchReport("2022-12-01", "2022-12-20"));
    }


}