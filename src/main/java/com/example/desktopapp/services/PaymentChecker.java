package com.example.desktopapp.services;

import com.example.desktopapp.entities.Customers;
import com.example.desktopapp.entities.services.Gym;
import com.example.desktopapp.entities.services.Users;
import com.example.desktopapp.models.CustomerDTO;
import com.example.desktopapp.models.GymDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.SQLException;
import java.time.LocalDate;

public class PaymentChecker {

    private final ObservableList<Customers> activeCustomers;
    private final ObservableList<Customers> outdatedCustomers;

    private final ObservableList<Customers> allCustomers;
    private final Users activeUser;

    public PaymentChecker(Users activeUser) throws SQLException {
        this.activeUser = activeUser;
        this.activeCustomers = FXCollections.observableArrayList();
        this.outdatedCustomers = FXCollections.observableArrayList();
        this.allCustomers = FXCollections.observableArrayList();

    }

    public Task<ObservableList<Customers>> FetchCustomersByGander = new Task<ObservableList<Customers>>() {
        @Override
        protected ObservableList<Customers> call() throws Exception {
            int i = 0;

            System.out.println("Active customers is " + CustomerDTO.fetchCustomersWithGender(activeUser).size());
            for (Customers customer : CustomerDTO.fetchCustomersWithGender(activeUser)) {
                i++;
                updateMessage("Loading..");
                updateProgress(i, CustomerDTO.limit);
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
    };


    public Task<ObservableList<Customers>> FetchAllCustomers = new Task<ObservableList<Customers>>() {
        @Override
        protected ObservableList<Customers> call() throws Exception {
            int i = 0;

            System.out.println("Active customers is " + CustomerDTO.fetchAllCustomersWithGender(activeUser).size());
            for (Customers customer : CustomerDTO.fetchCustomersWithGender(activeUser)) {
                i++;
                updateMessage("Loading..");
                updateProgress(i, CustomerDTO.limit);

                allCustomers.add(customer);
                System.out.println(customer.getFirstName() + " active " + customer.getPayments().get(0).getExpDate()+"---");

                Thread.sleep(100);
            }
            return null;
        }
    };

//    @Override
//    protected Void call() throws Exception {
//        int i = 0;
//
//        System.out.println("Active customers is " + CustomerDTO.fetchCustomersWithGender(activeUser).size());
//        for (Customers customer : CustomerDTO.fetchCustomersWithGender(activeUser)) {
//            i++;
//            updateMessage("Loading..");
//            updateProgress(i, CustomerDTO.limit);
//            if (customer.getPayments().get(0).getExpDate().isBefore(LocalDate.now())) {
//                System.out.println(customer.getFirstName() + " Outdated " + customer.getPayments().get(0).getExpDate());
//                outdatedCustomers.add(customer);
//            } else {
//                activeCustomers.add(customer);
//                System.out.println(customer.getFirstName() + " active " + customer.getPayments().get(0).getExpDate());
//            }
//            Thread.sleep(10);
//        }
//        return null;
//    }

    public ObservableList<Customers> getActiveCustomers() {
        return activeCustomers;
    }

    public ObservableList<Customers> getOutdatedCustomers() {
        return outdatedCustomers;
    }

    public ObservableList<Customers> getAllCustomers() {
        return allCustomers;
    }

    public Users getActiveUser() {
        return activeUser;
    }

    public Gym getCurrentGym() throws SQLException {
        return GymDTO.getCurrentGym();
    }
}
