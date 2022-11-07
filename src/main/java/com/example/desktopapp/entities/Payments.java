package com.example.desktopapp.entities;

import javafx.scene.shape.Box;

import java.time.LocalDate;

public class Payments {

    private int paymentID;
    private String paymentDate;
    private LocalDate expDate;
    private String month;
    private String year;
    private double amountPaid;
    private String paidBy;
    private double discount;
    private boolean poxing;
    private Box box;
    private String customerFK;
    private boolean online;

    public Payments(LocalDate expDate, double amountPaid, String paidBy, double discount, boolean poxing, Box box, String customerFK) {
        this.expDate = expDate;
        this.amountPaid = amountPaid;
        this.paidBy = paidBy;
        this.discount = discount;
        this.poxing = poxing;
        this.box = box;
        this.customerFK = customerFK;
    }

    public Payments(int paymentID, String paymentDate, LocalDate expDate, String month, String year, double amountPaid, String paidBy, double discount, boolean poxing, String customerFK, boolean online) {
        this.paymentID = paymentID;
        this.paymentDate = paymentDate;
        this.expDate = expDate;
        this.month = month;
        this.year = year;
        this.amountPaid = amountPaid;
        this.paidBy = paidBy;
        this.discount = discount;
        this.poxing = poxing;
        this.box = box;
        this.customerFK = customerFK;
        this.online = online;
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

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public double getDiscount() {
        return discount;
    }

    public boolean isPoxing() {
        return poxing;
    }

    public Box getBox() {
        return box;
    }

    public String getCustomerFK() {
        return customerFK;
    }

    public boolean isOnline() {
        return online;
    }

    @Override
    public String toString() {
        return "Payments{" +
                "paymentID=" + paymentID +
                ", paymentDate='" + paymentDate + '\'' +
                ", expDate='" + expDate + '\'' +
                ", box=" + box +
                ", customerFK='" + customerFK + '\'' +
                ", online=" + online +
                '}';
    }
}

