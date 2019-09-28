package com.finki.timesheets.model.dto;

public class MemberTotalSalary {
    private String fullName;
    private double totalSalary;

    public MemberTotalSalary(String fullName, double totalSalary) {
        this.fullName = fullName;
        this.totalSalary = totalSalary;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(double totalSalary) {
        this.totalSalary = totalSalary;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
