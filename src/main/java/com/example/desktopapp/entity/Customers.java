package com.example.desktopapp.entity;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public record Customers(
        int customerId, String firstname, String middleName,
        String lastName, String phone, String gander,
        String shift, String address, String image,
        double weight, String whoAdded,
        JFXButton information, JFXButton update,
        ObservableList<Payments> payments) {

    public Customers(String firstname, String middleName,
                     String lastName, String phone, String gander,
                     String shift, String address, String image,
                     double weight, String whoAdded) {

        this(0, firstname, middleName, lastName, phone, gander, shift, address, image,
                weight, whoAdded, null, null, null);

    }
}
