package com.example.desktopapp.models;

import com.example.desktopapp.entity.Customers;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDTOTest {

    @Test
    void insertCustomer() throws SQLException {

        Customers customer = new Customers("Hasan", "Sheekh", "Mahamuud", "4303925",
                "Male", "Afternoon", "tuurta", null, 67, "ogleh");

        CustomerDTO.insertCustomer(customer);
    }

    @Test
    void fetchAllCustomers() throws SQLException {
        System.out.println(CustomerDTO.fetchAllCustomer());
    }


    @Test
    void fetchSpecifictCustomers() throws SQLException {
        String customerQuery = "SELECT * FROM customers";

        String paymentQuery = "SELECT * FROM payments WHERE is_online=false AND pending=false AND\n" +
                "exp_date BETWEEN '2022-12-01' AND '2023-01-30';";

      //  System.out.println(CustomerDTO.fetchCustomersWithGenderWherePaymentIsOfflineANDExpDateBtw(customerQuery,paymentQuery));
    }
}