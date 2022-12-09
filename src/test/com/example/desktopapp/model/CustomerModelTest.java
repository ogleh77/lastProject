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

class CustomerModelTest {

    @Test
    void itShouldInsertCustomer() throws SQLException {

//        Customers customer = new Customers("Mohamed", "Saeed", "Ogleh", "4303924",
//                "Male", "Afternoon", "tuurta", null, 75, "Hamse", null);

        Customers customer2 = new Customers("Layla", "Muuse", "Ahmed", "4303922",
                "Male", "Afternoon", "tuurta", null, 75, "Hamse", null);
        // CustomerModel.insertCustomer(customer);
        CustomerModel.insertCustomer(customer2);
    }

    @Test
    void itShouldInsertPayment() throws SQLException {

        Payments payment = new Payments(LocalDate.now().plusDays(30), 12.0, "eDahab",
                1, true, null, 4303924);

        Payments payment2 = new Payments(LocalDate.now().minusDays(30), 12.0, "eDahab",
                1, true, null, 4303923);


        ObservableList<Payments> payments = FXCollections.observableArrayList();

        payments.add(0, payment2);
        payments.add(payment);

        Customers customer = new Customers("Hamse", "Abdi", "Ogleh", "4303922",
                "Male", "Afternoon", "tuurta", null, 75, "Hamse", payments);
        CustomerModel.insertPayment(customer);
    }

    @Test
    void itShouldUpdateCustomer() {
    }

    @Test
    void itShouldFetchCustomersWithGender() throws SQLException {

        Users user = new Users(1, null, null, null,
                "Male", null, null, null,
                null, "superAdmin");

        System.out.println(CustomerModel.fetchCustomersWithGender(user));
    }


    @Test
    void itShouldFetchThePayments() throws SQLException {

        System.out.println(CustomerModel.fetchPayments("4303924"));
    }


    @Test
    void itShouldInsertPaymentAndCustomersWithReport() throws SQLException {

        Payments payment = new Payments(LocalDate.now().plusDays(30), 12.0, "eDahab",
                1, true, null, 4303923);

        Payments payment2 = new Payments(LocalDate.now().minusDays(30), 12.0, "eDahab",
                1, true, null, 4303923);


        ObservableList<Payments> payments = FXCollections.observableArrayList();

        payments.add(0, payment);
        payments.add(payment2);

        Customers customer = new Customers("Hamse", "Abdi", "Ogleh", "4303923",
                "Male", "Afternoon", "tuurta", null, 75, "Hamse", payments);

        CustomerModel.insertCustomerWithPayment(customer);
    }
}