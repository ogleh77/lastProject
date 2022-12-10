package com.example.desktopapp.model;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.entity.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDTOTest {

    @Test
    void insertCustomer() throws SQLException {

        Customers customer = new Customers("Jama", "Ali", "Husein", "4303922",
                "Male", "afternoon", null, null, 0, "Ogleh", null);

        CustomerDTO.insertCustomer(customer);


    }

    @Test
    void updateCustomer() throws SQLException {
        Customers customer = new Customers(1, "Layla", "Osman", "Ahmed", "4303922",
                "Female", "afternoon", null, null, 0, "Ogleh", null, null, null);


        CustomerDTO.updateCustomer(customer);
    }

    @Test
    void fetchCustomersWithGender() throws SQLException {
        Users user = new Users(1, null, null, null,
                "Male", null, null, null,
                null, "superAdmin");

        System.out.println(CustomerDTO.fetchCustomersWithGender(user));


    }

    @Test
    void insertCustomerWithPaymentTransaction() throws SQLException {

        Payments payment = new Payments(LocalDate.now().plusDays(30), 12.0,
                "eDahab", 1.0, false, null, 4303924);

        ObservableList<Payments> payments = FXCollections.observableArrayList();

        payments.add(0, payment);


        Customers customer = new Customers("Layla", "Muuse", "Jama", "4303922",
                "Female", "afternoon", null, null, 0, "Ogleh", payments);


        //     PaymentDTO.insertCustomerWithPayment(customer);
    }


}