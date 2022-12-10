package com.example.desktopapp.model;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

public class test {


    @Test
    public void itShouldPendPayment() throws SQLException {
        PendingDTO.insertPending(CustomerModel.fetchPayments("4303922").get(0));
    }

    @Test
    public void itShouldLoadPendings() throws SQLException {
        System.out.println(PendingDTO.fetchPending());;
    }



}
