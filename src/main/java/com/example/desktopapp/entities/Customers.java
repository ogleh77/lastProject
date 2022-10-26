package com.example.desktopapp.entities;

import javafx.collections.ObservableList;

public class Customers {

    private int customerId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private String gander;
    private String shift;
    private String address;
    private String image;
    private double weight;
    private String whoAdded;
    private ObservableList<Payments> payments;

    public Customers(int customerId, String firstName, String middleName, String lastName, String phone, String gander, String shift, String address, String image, double weight, String whoAdded) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
        this.gander = gander;
        this.shift = shift;
        this.address = address;
        this.image = image;
        this.weight = weight;
        this.whoAdded = whoAdded;
    }

    public Customers(String firstName, String middleName, String lastName, String phone, String gander, String shift, String address, String image, double weight, String whoAdded) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
        this.gander = gander;
        this.shift = shift;
        this.address = address;
        this.image = image;
        this.weight = weight;
        this.whoAdded = whoAdded;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getGander() {
        return gander;
    }

    public String getShift() {
        return shift;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public double getWeight() {
        return weight;
    }

    public String getWhoAdded() {
        return whoAdded;
    }

    public ObservableList<Payments> getPayments() {
        return payments;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPayments(ObservableList<Payments> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", gander='" + gander + '\'' +
                ", shift='" + shift + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                ", weight=" + weight +
                ", whoAdded='" + whoAdded + '\'' +
                ", payments=" + payments +
                '}';
    }
}
