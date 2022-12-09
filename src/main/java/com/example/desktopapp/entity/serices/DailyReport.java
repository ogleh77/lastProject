package com.example.desktopapp.entity.serices;

public record DailyReport(String reportDate, String reportMonth, int registrations,
                          int male, int female, int vipBox) {
}
