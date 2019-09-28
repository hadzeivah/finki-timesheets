package com.finki.timesheets.model;

import com.finki.timesheets.model.dto.ProjectTotalSalary;

import java.util.List;
import java.util.Map;

public class Report {

    private List<ProjectTotalSalary> totalSalaryPerProject;
    private Map<Project, Map<Member, Long>> totalSalaryPerProjectMember;


    public Report() {
    }

    public Report(List<ProjectTotalSalary> totalSalaryPerProject, Map<Project, Map<Member, Long>> totalSalaryPerProjectMember) {
        this.totalSalaryPerProject = totalSalaryPerProject;
        this.totalSalaryPerProjectMember = totalSalaryPerProjectMember;
    }

    public List<ProjectTotalSalary> getTotalSalaryPerProject() {
        return totalSalaryPerProject;
    }

    public void setTotalSalaryPerProject(List<ProjectTotalSalary> totalSalaryPerProject) {
        this.totalSalaryPerProject = totalSalaryPerProject;
    }

    public Map<Project, Map<Member, Long>> getTotalSalaryPerProjectMember() {
        return totalSalaryPerProjectMember;
    }

    public void setTotalSalaryPerProjectMember(Map<Project, Map<Member, Long>> totalSalaryPerProjectMember) {
        this.totalSalaryPerProjectMember = totalSalaryPerProjectMember;
    }
}
