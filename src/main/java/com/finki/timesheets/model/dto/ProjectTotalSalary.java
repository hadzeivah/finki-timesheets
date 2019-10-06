package com.finki.timesheets.model.dto;

import com.finki.timesheets.model.Project;

import java.util.List;

public class ProjectTotalSalary {
    private Long id;
    private String name;
    private Long estimatedBudget;
    private double totalSalary;
    private List<MemberTotalSalary> memberTotalSalaryList;

    public ProjectTotalSalary(Project project, double totalSalary, List<MemberTotalSalary> memberTotalSalaryList) {
        this.id = project.getId();
        this.name = project.getName();
        this.estimatedBudget = project.getEstimatedBudget();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
