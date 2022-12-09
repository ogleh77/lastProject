package com.example.desktopapp.entity;

import com.example.desktopapp.entity.serices.Box;

import java.time.LocalDate;

public record Payments(int paymentId, String paymentDate, LocalDate expDate, String month, String year,
                       double amountPaid, String paidBy, double discount, boolean poxing, Box box,
                       int customerPhone, boolean online, boolean pending) {

    public Payments(LocalDate expDate, double amountPaid, String paidBy, double discount,
                    boolean poxing, Box box, int customerPhone) {

        this(0, null, expDate, null, null, amountPaid, paidBy,
                discount, poxing, box, customerPhone, false, false);
    }
}
