package com.example.desktopapp.controllers.services;

public class MonthlyReport {
    private final String reportMonth;
    private final int totalRegistration;
    private final int totalMale;
    private final int totalFemale;
    private final int totalVipBox;

    public MonthlyReport(String reportMonth, int totalRegistration, int totalMale, int totalFemale, int totalVipBox) {
        this.reportMonth = reportMonth;
        this.totalRegistration = totalRegistration;

        this.totalMale = totalMale;
        this.totalFemale = totalFemale;
        this.totalVipBox = totalVipBox;
    }

    public String getReportMonth() {
        return reportMonth;
    }

    public int getTotalRegistration() {
        return totalRegistration;
    }

    public int getTotalMale() {
        return totalMale;
    }

    public int getTotalFemale() {
        return totalFemale;
    }

    public int getTotalVipBox() {
        return totalVipBox;
    }

    @Override
    public String toString() {
        return "MonthlyReport{" +
                "reportMonth='" + reportMonth + '\'' +
                ", totalRegistration=" + totalRegistration +
                ", totalMale=" + totalMale +
                ", totalFemale=" + totalFemale +
                ", totalVipBox=" + totalVipBox +
                '}';
    }
}
