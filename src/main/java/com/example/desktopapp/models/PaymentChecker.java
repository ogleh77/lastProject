package com.example.desktopapp.models;

import com.example.desktopapp.entities.Customers;
import com.example.desktopapp.entities.Payments;
import com.example.desktopapp.entities.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class PaymentChecker extends Task<Void> {

    private final ObservableList<Customers> activeCustomers;
    private final ObservableList<Customers> outdatedCustomers;
    private final Users activeUser;

    public PaymentChecker(Users activeUser) {
        this.activeUser = activeUser;
        this.activeCustomers = FXCollections.observableArrayList();
        this.outdatedCustomers = FXCollections.observableArrayList();
    }

    @Override
    protected Void call() throws Exception {
        int i = 0;
        for (Customers customer : CustomerDAO.fetchPaymentsWithGender(activeUser)) {
            i++;
            updateMessage("Loading..");
            updateProgress(i, CustomerDAO.limit);
            if (customer.getPayments().get(0).getExpDate().isBefore(LocalDate.now())) {
                System.out.println(customer.getFirstName() + " Outdated " + customer.getPayments().get(0).getExpDate());
                outdatedCustomers.add(customer);
            } else {
                activeCustomers.add(customer);
                System.out.println(customer.getFirstName() + " active " + customer.getPayments().get(0).getExpDate());
            }
            Thread.sleep(10);
        }
        return null;
    }

    public ObservableList<Customers> getActiveCustomers() {
        return activeCustomers;
    }

    public ObservableList<Customers> getOutdatedCustomers() {
        return outdatedCustomers;
    }

    public Users getActiveUser() {
        return activeUser;
    }
}
