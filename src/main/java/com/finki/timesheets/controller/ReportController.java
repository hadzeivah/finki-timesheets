package com.finki.timesheets.controller;


import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.dto.ProjectTotalSalary;
import com.finki.timesheets.service.ProjectService;
import com.finki.timesheets.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ProjectService projectService;
    private ReportService reportService;

    @Autowired
    public ReportController(ProjectService projectService, ReportService reportService) {
        this.projectService = projectService;
        this.reportService = reportService;
    }

    @GetMapping()
    public List<ProjectTotalSalary> getDetailedReport() {
        List<Project> projects = projectService.findAll();
        return this.reportService.calculateTotalSalaryByProject(projects);
    }

    @GetMapping(value = "/exportExcel", produces = "application/vnd.ms-excel")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getExcelReport() {
        List<Project> projects = projectService.findAll();

        ByteArrayInputStream generatedExcel = this.reportService.exportReportToExcel(projects);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Access-Control-Expose-Headers", "Content-Disposition")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx")
                .body(new InputStreamResource(generatedExcel));

    }
}
