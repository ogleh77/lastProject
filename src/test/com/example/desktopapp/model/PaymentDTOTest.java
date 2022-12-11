package com.example.desktopapp.model;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.entity.serices.Box;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDTOTest {
    @Test
    void itShouldInsertPayment() throws SQLException {

        Box box = new Box(3, "Box 1", true);
        Payments payment = new Payments(LocalDate.now().plusDays(30), 12.0,
                "eDahab", 1.0, false, box, 4303924);


        PaymentDTO.insertPayment("4303924", "Male", payment);


        System.out.println("payment " + PaymentDTO.connection + " customers " + CustomerDTO.connection);
    }


    @Test
    void itShouldInsertCustomerWithPayment() throws SQLException {

        Box box = new Box(1, "Box 1", true);


        Payments payment = new Payments(LocalDate.now().minusDays(30), 12.0,
                "Zaad service", 1.0, false, box, 4303920);

        Customers customer = new Customers("Layla", "Muuse", "Ali", "4303920",
                "Female", "afternoon", null, null, 0, "Ogleh", null);


        PaymentDTO.insertCustomerWithPayment(customer, payment);

    }


    @Test
    void itShouldLoadOnlinePayments() throws SQLException {
        System.out.println(PaymentDTO.fetchPaymentsWhereIsOnline());
    }


    @Test
    void itShouldLoadCustomersPayments() throws SQLException {
        System.out.println(PaymentDTO.fetchPayments("4303920"));
    }


    @Test
    void itShouldLoadSinglePayment() throws SQLException {
        System.out.println(PaymentDTO.payment(5));
    }


    @Test
    void itShouldOutDatePayment() throws SQLException {

        PaymentDTO.paymentOutDated(PaymentDTO.payment(6));
    }
}