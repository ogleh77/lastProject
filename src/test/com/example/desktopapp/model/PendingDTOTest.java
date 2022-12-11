package com.example.desktopapp.model;

import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.entity.serices.Pending;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;


class PendingDTOTest {

    @Test
    void pendPayment() throws SQLException {

        Payments payment = PaymentDTO.payment(5);


        if (payment.online()) {

            LocalDate expDate = payment.expDate();

            LocalDate pendingDate = LocalDate.now();

            Period period = Period.between(pendingDate, expDate);

            int daysRemain = period.getDays();

            PendingDTO.pendPayment(payment, daysRemain);
            System.out.println("Done..");

        }
    }

    @Test
    void fetchPendingPayments() throws SQLException {

        System.out.println(PendingDTO.fetchPendingPayments());
    }


    @Test
    void fetchSinglePayment() throws SQLException {

        System.out.println(PendingDTO.fetchSinglePendingPayment(1));
    }

    @Test
    void rePend() throws SQLException {
        Pending pending = PendingDTO.fetchSinglePendingPayment(1);

        PendingDTO.updatePendingPayment(pending);
    }

}