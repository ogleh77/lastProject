package com.example.desktopapp.helpers;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Payments;
import com.example.desktopapp.entity.Users;
import com.example.desktopapp.model.CustomerDTO;
import com.example.desktopapp.model.PaymentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.time.LocalDate;

public class PaymentChecker {

    private final ObservableList<Customers> outDatedCustomers;
    private final ObservableList<Customers> warningCustomers;

    private final ObservableList<Customers> allCustomers;
    private final Users activeUser;

    public PaymentChecker(Users activeUser) {
        this.activeUser = activeUser;
        this.outDatedCustomers = FXCollections.observableArrayList();
        this.warningCustomers = FXCollections.observableArrayList();
        this.allCustomers = FXCollections.observableArrayList();
    }


    public Task<Void> checkerOutDated = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            int i = 0;
            for (Customers customer : CustomerDTO.fetchCustomersWithGenderWherePaymentIsOnline(activeUser)) {
                i++;
                updateMessage("Loading..");
                updateProgress(i, CustomerDTO.limit);
                if (customer.getPayments().get(0).getExpDate().isBefore(LocalDate.now())) {
                    System.out.println(customer.getFirstName() + " Outdated " + customer.getPayments().get(0).getExpDate());
                    outDatedCustomers.add(customer);

                } else if (customer.getPayments().get(0).getExpDate().isEqual(LocalDate.now().plusDays(2))) {
                    warningCustomers.add(customer);
                    System.out.println(customer.getFirstName() + " is warning " + customer.getPayments().get(0).getExpDate());
                }
                Thread.sleep(10);
            }
            return null;
        }
    };


    public Task<Void> fetchAllCustomers = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            int i = 0;
            for (Customers customer : CustomerDTO.fetchAllCustomer()) {

                i++;
                updateMessage("Loading..");
                updateProgress(i, CustomerDTO.numberOfCustomers);

                allCustomers.add(customer);
                Thread.sleep(1000);
            }
            return null;
        }
    };


    public ObservableList<Customers> getOutDatedCustomers() {
        return outDatedCustomers;
    }

    public ObservableList<Customers> getWarningCustomers() {
        return warningCustomers;
    }

    public ObservableList<Customers> getAllCustomers() {
        return allCustomers;
    }

    public Users getActiveUser() {
        return activeUser;
    }
}
