package com.finki.timesheets.model.dto;

import java.util.List;

public class ProjectTotalSalary {
    private String name;
    private Long estimatedBudget;
    private double totalSalary;
    private List<MemberTotalSalary> memberTotalSalaryList;

    public ProjectTotalSalary(String name, Long estimatedBudget, double totalSalary, List<MemberTotalSalary> memberTotalSalaryList) {
        this.name = name;
        this.estimatedBudget = estimatedBudget;
        this.totalSalary = totalSalary;
        this.memberTotalSalaryList = memberTotalSalaryList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getEstimatedBudget() {
        return estimatedBudget;
    }

    public void setEstimatedBudget(Long estimatedBudget) {
        this.estimatedBudget = estimatedBudget;
    }
}
