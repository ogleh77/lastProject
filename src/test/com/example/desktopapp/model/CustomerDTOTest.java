package com.example.desktopapp.model;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.entity.Users;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDTOTest {

    @Test
    void insertCustomer() throws SQLException {


//        Customers customer = new Customers("Jama", "Ali", "Husein", "4303922",
//                "Male", "afternoon", null, null, 0, "Ogleh", null);
//
//        CustomerDTO.insertCustomer(customer);
        //Payments payment=PaymentDTO.fetchSinglePayment(9);


    //   LocalDate exp = payment.getExpDate();
//
      //  LocalDate pendingDate = LocalDate.now();

       // Period period = Period.between(exp, pendingDate);





    }

    @Test
    void updateCustomer() {
    }

    @Test
    void fetchCustomersAll() throws SQLException {

        System.out.println(CustomerDTO.fetchAllCustomer());
    }

    @Test
    void fetchCustomersWithGenderWherePaymentIsOnline() throws SQLException {

        Users user = new Users(null, null, null, "Female",
                null, null, null, null, "super_admin");

        System.out.println(CustomerDTO.fetchCustomersWithGenderWherePaymentIsOnline(user));

    }
}