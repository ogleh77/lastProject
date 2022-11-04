package com.example.desktopapp.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customers {

    private int customerId;
    private String firstName;

    private String gander;
    private String phone;
    private ObservableList<Payments> payments;

    public Customers(String firstName, String gander, String phone) {
        this.firstName = firstName;
        this.gander = gander;
        this.phone = phone;
    }

    public Customers(int customerId, String firstName, String phone, String gander) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.gander = gander;
        this.phone = phone;
        payments = FXCollections.observableArrayList();
    }



    public int getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getGander() {
        return gander;
    }

    public ObservableList<Payments> getPayments() {
        return payments;
    }

    public void setPayments(ObservableList<Payments> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", gander='" + gander + '\'' +
                ", payments=" + payments +
                '}';
    }
}
