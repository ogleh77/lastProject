package com.example.desktopapp.services;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.services.Users;
import com.example.desktopapp.models.CustomerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.SQLException;
import java.time.LocalDate;

public class PaymentChecker {
    private final ObservableList<Customers> activeCustomers;
    private final ObservableList<Customers> outdatedCustomers;

    private ObservableList<Customers> allCustomers;

    private final Users activeUser;

    public PaymentChecker(Users activeUser) {
        this.activeCustomers = FXCollections.observableArrayList();
        this.outdatedCustomers = FXCollections.observableArrayList();
        this.allCustomers = FXCollections.observableArrayList();
        this.activeUser = activeUser;
    }


    public Task<ObservableList<Customers>> FetchCustomersByGander = new Task<>() {

        @Override
        protected ObservableList<Customers> call() throws Exception {
            int i = 0;
            for (Customers customer : CustomerDTO.fetchAllPayments(activeUser)) {
                i++;
                updateMessage("Loading..");
                updateProgress(i, CustomerDTO.limit);

                if (customer.getPayment() != null) {
                    if (customer.getPayment().getExpDate().isBefore(LocalDate.now())) {
                        outdatedCustomers.add(customer);
                    } else {
                        activeCustomers.add(customer);
                    }
                }

                Thread.sleep(100);
                allCustomers.add(customer);
            }


            return null;
        }
    };

    public ObservableList<Customers> getActiveCustomers() {
        return activeCustomers;
    }

    public ObservableList<Customers> getAllCustomers() {
        return allCustomers;
    }

    public ObservableList<Customers> getOutdatedCustomers() {
        return outdatedCustomers;
    }

    public Users getActiveUser() {
        return activeUser;
    }
}
