package com.finki.timesheets.model.dto;

import java.util.List;

public class ProjectTotalSalary {
    private String name;
    private Long budget;
    private double totalSalary;
    private List<MemberTotalSalary> memberTotalSalaryList;

    public ProjectTotalSalary(String name, Long budget, double totalSalary, List<MemberTotalSalary> memberTotalSalaryList) {
        this.name = name;
        this.budget = budget;
        this.totalSalary = totalSalary;
        this.memberTotalSalaryList = memberTotalSalaryList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(Long totalSalary) {
        this.totalSalary = totalSalary;
    }

    public List<MemberTotalSalary> getMemberTotalSalaryList() {
        return memberTotalSalaryList;
    }

    public void setMemberTotalSalaryList(List<MemberTotalSalary> memberTotalSalaryList) {
        this.memberTotalSalaryList = memberTotalSalaryList;
    }
}
