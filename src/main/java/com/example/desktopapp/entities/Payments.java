package com.example.desktopapp.entities;

public class Payments {

    private int paymentID;
    private String paymentDate;

    private String expDate;
    private String month;
    private String year;
    private final double amountPaid;
    private final String paidBy;
    private final double discount;
    private final boolean poxing;

    private final String comment;
    private Box box;

    public Payments(double amountPaid, String comment, String paidBy, double discount, boolean poxing) {
        this.amountPaid = amountPaid;
        this.comment = comment;
        this.paidBy = paidBy;
        this.discount = discount;
        this.poxing = poxing;
    }

    public Payments(int paymentID, String paymentDate, String expDate, String month, String year, double amountPaid, String paidBy, double discount, boolean poxing, String comment) {
        this.paymentID = paymentID;
        this.paymentDate = paymentDate;
        this.expDate = expDate;
        this.month = month;
        this.year = year;
        this.amountPaid = amountPaid;
        this.paidBy = paidBy;
        this.discount = discount;
        this.poxing = poxing;
        this.comment = comment;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public String getPaymentDate() {
        return paymentDate;
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

    public String getComment() {
        return comment;
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

    public void setBox(Box box) {
        this.box = box;
    }

    @Override
    public String toString() {
        return "Payments{" +
                "paymentID=" + paymentID +
                ", paymentDate='" + paymentDate + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", amountPaid=" + amountPaid +
                ", comment='" + comment + '\'' +
                ", paidBy='" + paidBy + '\'' +
                ", discount=" + discount +
                ", poxing=" + poxing +
                ", \n\nbox=" + box +
                '}';
    }
}
