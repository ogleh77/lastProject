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
    private ObservableList<Payments> payments;

    public Customers(int customerId, String firstName, String middleName, String lastName, String phone, String gander, String shift, String address, String image, double weight) {
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
    }

    public Customers(String firstName, String middleName, String lastName, String phone, String gander, String shift, String address, String image, double weight) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
        this.gander = gander;
        this.shift = shift;
        this.address = address;
        this.image = image;
        this.weight = weight;
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
                ", payments=" + payments +
                '}';
    }
}
