package com.example.desktopapp.models;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.entity.Users;
import com.example.desktopapp.entity.serices.Box;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDTOTest {

    @Test
    void insertPayment() throws SQLException {

        Payments payment = new Payments(LocalDate.now().minusDays(20), 12.0, "eDahab", 0.0, true, "4303924");

        PaymentDTO.insertPayment("4303925", "Male", payment);


    }

    @Test
    void insertCustomerWithPayment() throws SQLException {
        Customers customer = new Customers("Khadar", "Xuseen", "Cabdilahi", "4303930", "Male", "morning", null, null, 0, "Ogleh");

        Payments payment = new Payments(LocalDate.now().plusDays(2), 12.0, "Zaad", 1.0, false, "4303930");

        PaymentDTO.insertCustomerWithPayment(customer, payment);

    }


    @Test
    void fetchSinglePayment() throws SQLException {
        System.out.println(PaymentDTO.fetchSinglePayment(2));
    }


    @Test
    void fetchIsOnlinePayment() throws SQLException {
        System.out.println(PaymentDTO.fetchPaymentsWhereIsOnline("4303925"));
    }


    @Test
    void fetchCustomerThoseHaveOnlinePayment() throws SQLException {
        Users user = new Users(null, null, null, "Male", null, null, null, null, "super_admin");


        //    System.out.println(CustomerDTO.fetchCustomersWithGenderWherePaymentIsOnline(user));


        for (Customers customer : CustomerDTO.fetchCustomersWithGenderWherePaymentIsOnline(user)) {
            LocalDate expDate = customer.getPayments().get(0).getExpDate();
            if (expDate.isBefore(LocalDate.now())) {
                System.out.println("Out dated..");
                PaymentDTO.paymentOutDated(customer.getPayments().get(0));
                System.out.println(customer);
            } else if (expDate.isEqual(LocalDate.now().plusDays(2)) || expDate.isEqual(LocalDate.now().plusDays(1))) {
                System.out.println("Waring..");
                System.out.println(customer);
            } else {
                System.out.println("Normal");
                System.out.println(customer);
            }
        }
    }

    @Test
    void fetchCustomerThoseHaveOfflinePayment() throws SQLException {
        Users user = new Users(null, null, null, "Male",
                null, null, null, null, "super_admin");


        System.out.println(CustomerDTO.fetchCustomersWithGenderWherePaymentIsOffline(user));


//        for (Customers customer : CustomerDTO.fetchCustomersWithGenderWherePaymentIsOnline(user)) {
//            LocalDate expDate = customer.getPayments().get(0).getExpDate();
//            if (expDate.isBefore(LocalDate.now())) {
//                System.out.println("Out dated..");
//                PaymentDTO.paymentOutDated(customer.getPayments().get(0));
//                System.out.println(customer);
//            } else if (expDate.isEqual(LocalDate.now().plusDays(2)) || expDate.isEqual(LocalDate.now().plusDays(1))) {
//                System.out.println("Waring..");
//                System.out.println(customer);
//            } else {
//                System.out.println("Normal");
//                System.out.println(customer);
//            }
//        }
//    }

    }


    @Test
    void fetchDatePayment() throws SQLException {

        System.out.println(PaymentDTO.fetchPaymentsWhereIsOfflineANDDateBetween("4303930",
                "2022-11-01", "2023-01-30"));


    }
}
