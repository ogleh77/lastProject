package com.example.desktopapp.entity;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
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
    private JFXButton information;
    private JFXButton update;
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

        this.information = new JFXButton("information");
        this.update = new JFXButton("update");
        information.setStyle("-fx-background-color: #1e6e66;-fx-text-fill: white;-fx-pref-width: 100;-fx-font-size: 15");
        update.setStyle("-fx-background-color: dodgerblue;-fx-text-fill: white;-fx-pref-width: 100;-fx-font-size: 15");

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

    public JFXButton getInformation() {
        return information;
    }

    public JFXButton getUpdate() {
        return update;
    }

    public ObservableList<Payments> getPayments() {
        return payments;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGander(String gander) {
        this.gander = gander;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setWhoAdded(String whoAdded) {
        this.whoAdded = whoAdded;
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
                ", information=" + information +
                ", update=" + update +
                ",\n----------- payments=" + payments +
                '}';
    }
}
