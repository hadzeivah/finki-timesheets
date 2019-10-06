package com.finki.timesheets.model.dto;

import com.finki.timesheets.model.Member;

public class MemberTotalSalary {
    private Long id;
    private String fullName;
    private String position;
    private double salary;
    private double totalSalary;

    public MemberTotalSalary(Member member, String position, double salary, double totalSalary) {
        this.id = member.getId();
        this.fullName = member.getFullName();
        this.position = position;
        this.salary = salary;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
