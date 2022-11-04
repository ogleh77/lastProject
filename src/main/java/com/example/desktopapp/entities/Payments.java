package com.example.desktopapp.entities;

import java.time.LocalDate;

public class Payments {

    private int paymentID;
    private String paymentDate;

    private LocalDate expDate;

    private String customerFK;
    private Box box;


    public Payments(String paymentDate, LocalDate expDate) {
        this.paymentDate = paymentDate;
        this.expDate = expDate;
    }

    public Payments(int paymentID, String paymentDate, LocalDate expDate, String customerFK) {
        this.paymentID = paymentID;
        this.paymentDate = paymentDate;
        this.expDate = expDate;
        this.customerFK = customerFK;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public String getCustomerFK() {
        return customerFK;
    }

    @Override
    public String toString() {
        return "Payments{" +
                "paymentID=" + paymentID +
                ", paymentDate='" + paymentDate + '\'' +
                ", expDate='" + expDate + '\'' +
                ", box=" + box +
                '}';
    }
}
