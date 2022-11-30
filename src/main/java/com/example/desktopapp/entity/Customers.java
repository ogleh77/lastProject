package com.example.desktopapp.entity;


import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Customers {

    private int customerId;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String phone;
    private final String gander;
    private final String shift;
    private final String address;
    private final String image;
    private final double weight;
    private final String whoAdded;
    private JFXButton information;
    private JFXButton update;

    private Payments payment;
    private ObservableList<Payments> payments = FXCollections.observableArrayList();

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
        information.setStyle("-fx-background-color: #1e6e66;-fx-text-fill: white");
        update.setStyle("-fx-background-color: dodgerblue;-fx-text-fill: white");


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
        this.payments = FXCollections.observableArrayList();
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

    public Payments getPayment() {
        return payment;
    }
    //----------------------------_Setters-------------------------


    public void setPayment(Payments payment) {
        this.payment = payment;
    }

    public void setPayments(ObservableList<Payments> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gander='" + gander + '\'' +
                "\n\n, payment=" + payment +
                '}';
    }
}
