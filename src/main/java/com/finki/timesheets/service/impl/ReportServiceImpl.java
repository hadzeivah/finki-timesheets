package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Item;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.ReportByIO;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.model.dto.MemberTotalSalary;
import com.finki.timesheets.model.dto.ProjectTotalSalary;
import com.finki.timesheets.repository.ReportByIORepository;
import com.finki.timesheets.service.ReportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service(value = "ReportService")
public class ReportServiceImpl implements ReportService {

    private ReportByIORepository reportByIORepository;

    public ReportServiceImpl(ReportByIORepository reportByIORepository) {
        this.reportByIORepository = reportByIORepository;
    }

    @Override
    public List<ProjectTotalSalary> calculateTotalSalaryByProject(List<Project> projects) {
        List<ProjectTotalSalary> projectTotalSalaries = new ArrayList<>();

        projects.forEach(project -> {
            Set<Timesheet> timesheets = project.getTimesheets();
            List<MemberTotalSalary> memberTotalSalaries = new ArrayList<>();
            timesheets.forEach(timesheet -> {
                double memberSalary = timesheet.getProjectPosition().getSalary();
                double total = (timesheet.getItems().stream().mapToLong(Item::getHours).sum() / 8.0) * memberSalary;
                memberTotalSalaries.add(new MemberTotalSalary(timesheet.getMember(), timesheet.getProjectPosition().getPosition().getName(), memberSalary, total));
            });
            projectTotalSalaries.add(new ProjectTotalSalary(project, memberTotalSalaries.stream().mapToDouble(MemberTotalSalary::getTotalSalary).sum(), memberTotalSalaries));
        });
        return projectTotalSalaries;
    }

    @Override
    public List<ReportByIO> getReportTotalByIntellectualOutput() {
        return reportByIORepository.findAll();
    }

    @Override
    public double calculateTotalSalaryForProject(Project project) {

        Set<Timesheet> timesheets = project.getTimesheets();
        double totalSalary = 0L;
        for (Timesheet timesheet : timesheets) {
            double totalProjectMember = (timesheet.getItems().stream().mapToLong(Item::getHours).sum() / 8.0) * timesheet.getProjectPosition().getSalary();
            totalSalary += totalProjectMember;
        }
        return totalSalary;
    }


    @Override
    public ByteArrayInputStream exportReportToExcel(List<Project> projects) {

        String[] columns = {"Project", "Member", "Total", "Salary", "Position", "Budget", "Difference"};

        List<ProjectTotalSalary> projectTotalSalaries = this.calculateTotalSalaryByProject(projects);

        Workbook workbook = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();

        Sheet sheet = workbook.createSheet("Total salary by projects");
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);


        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        for (ProjectTotalSalary projectTotalSalary : projectTotalSalaries) {
            Row projectRow = sheet.createRow(rowNum++);

            projectRow.createCell(0)
                    .setCellValue(projectTotalSalary.getName());

            projectRow.createCell(2)
                    .setCellValue(projectTotalSalary.getTotalSalary());

            projectRow.createCell(5)
                    .setCellValue(projectTotalSalary.getEstimatedBudget());

            projectRow.createCell(6)
                    .setCellValue(projectTotalSalary.getEstimatedBudget() - projectTotalSalary.getTotalSalary());

            List<MemberTotalSalary> memberTotalSalaries = projectTotalSalary.getMemberTotalSalaryList();

            for (MemberTotalSalary memberTotalSalary : memberTotalSalaries) {

                Row memberRow = sheet.createRow(rowNum++);
                memberRow.createCell(1)
                        .setCellValue(memberTotalSalary.getFullName());

                memberRow.createCell(2)
                        .setCellValue(memberTotalSalary.getTotalSalary());

                memberRow.createCell(3)
                        .setCellValue(memberTotalSalary.getSalary());

                memberRow.createCell(4)
                        .setCellValue(memberTotalSalary.getPosition());

            }


        }

        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }


        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            workbook.write(out);
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());

    }
}
