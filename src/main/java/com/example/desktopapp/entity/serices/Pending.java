package com.example.desktopapp.entity.serices;

import com.example.desktopapp.entity.Payments;

import java.time.LocalDate;

public record Pending(int pendingId, String pendingDate, int daysRemain,
                      Payments payment, boolean isPending) {

    public Pending(int daysRemain) {
        this(0, null, daysRemain, null, false);
    }


}
