package com.finki.timesheets.service;

import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.dto.ProjectTotalSalary;

import java.util.List;

public interface ReportService {
    List<ProjectTotalSalary> calculateTotalSalaryByProject(List<Project> projects);

    double calculateTotalSalaryForProject(Project project);

}
