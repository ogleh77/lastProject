package com.example.desktopapp.entity;

public class DailyReport {
    private String reportDate;
    private String reportMonth;
    private int registrations;
    private int male;
    private int female;
    private int vipBox;

    public DailyReport(String reportDate, String reportMonth, int registrations, int male, int female, int vipBox) {
        this.reportDate = reportDate;
        this.reportMonth = reportMonth;
        this.registrations = registrations;
        this.male = male;
        this.female = female;
        this.vipBox = vipBox;
    }

    public DailyReport(int registrations, int male, int female, int vipBox) {
        this.registrations = registrations;
        this.male = male;
        this.female = female;
        this.vipBox = vipBox;
    }

    public String getReportDate() {
        return reportDate;
    }

    public String getReportMonth() {
        return reportMonth;
    }

    public int getRegistrations() {
        return registrations;
    }

    public int getMale() {
        return male;
    }

    public int getFemale() {
        return female;
    }

    public int getVipBox() {
        return vipBox;
    }

    @Override
    public String toString() {
        return "DailyReport{" +
                "reportDate='" + reportDate + '\'' +
                ", reportMonth='" + reportMonth + '\'' +
                ", registrations=" + registrations +
                ", male=" + male +
                ", female=" + female +
                ", vipBox=" + vipBox +
                '}';
    }
}
