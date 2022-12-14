package com.example.desktopapp.helpers;

import com.example.desktopapp.entity.Customers;
import com.example.desktopapp.entity.Users;
import com.example.desktopapp.models.CustomerDTO;
import com.example.desktopapp.models.PaymentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.SQLException;
import java.time.LocalDate;

public class PaymentChecker {

    private final ObservableList<Customers> outDatedCustomers;
    private final ObservableList<Customers> warningCustomers;

    private ObservableList<Customers> allCustomers;
    private final Users activeUser;

    public PaymentChecker(Users activeUser) {
        this.activeUser = activeUser;
        this.outDatedCustomers = FXCollections.observableArrayList();
        this.warningCustomers = FXCollections.observableArrayList();

        try {
            this.allCustomers = FXCollections.observableArrayList(CustomerDTO.fetchAllCustomer());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Task<Void> checkerOutDated = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            int i = 0;
            for (Customers customer : CustomerDTO.fetchCustomersWithGenderWherePaymentIsOnline(activeUser)) {
                i++;
                updateMessage("Loading..");
                updateProgress(i, CustomerDTO.limit);
                LocalDate expDate = customer.getPayments().get(0).getExpDate();
                if (expDate.isBefore(LocalDate.now())) {
                    System.out.println("Out dated..");
                    PaymentDTO.paymentOutDated(customer.getPayments().get(0));
                    warningCustomers.add(customer);
                } else if (expDate.isEqual(LocalDate.now().plusDays(2)) ||
                        expDate.isEqual(LocalDate.now().plusDays(1))) {
                    System.out.println("Waring..");
                    System.out.println(customer);
                } else {
                    System.out.println("Normal");
                    warningCustomers.add(customer);
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

                // allCustomers.add(customer);
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
