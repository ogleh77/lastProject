package com.example.desktopapp.model;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDTOTest {

    @Test
    void insertPayment() throws SQLException {

        Payments payment = new Payments(LocalDate.now().minusDays(30), 12.0,
                "ZaadService", 1.0, false, null, 4303924);

        PaymentDTO.insertPayment("4303924", "Male", payment);
    }

    @Test
    void insertCustomerWithPayment() throws SQLException {
        Customers customer = new Customers("Luul", "Jama", "Aadan", "4303920",
                "Female", "morning", null, null, 0, "Ogleh", null);

        Payments payment = new Payments(LocalDate.now().plusDays(30), 12.0,
                "eDahab", 1.0, false, null, 4303920);

        PaymentDTO.insertCustomerWithPayment(customer, payment);
    }

    @Test
    void paymentOutDated() {

    }

    @Test
    void fetchPayments() {
        //System.out.println();
    }

    @Test
    void fetchPaymentsWhereIsOnline() {
    }

    @Test
    void payment() {
    }

    @Test
    void setTookBoxIsReadyTrue() {
    }
}