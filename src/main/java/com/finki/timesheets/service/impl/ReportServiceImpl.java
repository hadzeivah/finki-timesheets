package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Item;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.model.dto.MemberTotalSalary;
import com.finki.timesheets.model.dto.ProjectTotalSalary;
import com.finki.timesheets.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service(value = "ReportService")
public class ReportServiceImpl implements ReportService {

    @Override
    public List<ProjectTotalSalary> calculateTotalSalaryByProject(List<Project> projects) {
        List<ProjectTotalSalary> projectTotalSalaries = new ArrayList<>();

        projects.forEach(project -> {
            Set<Timesheet> timesheets = project.getTimesheets();
            List<MemberTotalSalary> memberTotalSalaries = new ArrayList<>();
            timesheets.forEach(timesheet -> {
                double total = (timesheet.getItems().stream().mapToLong(Item::getHours).sum() / 24.0) * timesheet.getPositionSalary().getSalary();
                memberTotalSalaries.add(new MemberTotalSalary(timesheet.getMember().getFullName(), total));
            });
            projectTotalSalaries.add(new ProjectTotalSalary(project.getName(), project.getEstimatedBudget(), memberTotalSalaries.stream().mapToDouble(MemberTotalSalary::getTotalSalary).sum(), memberTotalSalaries));
        });
        return projectTotalSalaries;
    }

    @Override
    public double calculateTotalSalaryForProject(Project project) {

        Set<Timesheet> timesheets = project.getTimesheets();
        double totalSalary = 0L;
        for (Timesheet timesheet : timesheets) {
            double totalProjectMember = (timesheet.getItems().stream().mapToLong(Item::getHours).sum() / 24.0) * timesheet.getPositionSalary().getSalary();
            totalSalary += totalProjectMember;
        }
        return totalSalary;
    }
}
