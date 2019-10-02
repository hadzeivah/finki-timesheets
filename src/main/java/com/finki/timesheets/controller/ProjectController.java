package com.finki.timesheets.controller;


import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.dto.ProjectPositionDto;
import com.finki.timesheets.model.dto.ProjectTotalSalary;
import com.finki.timesheets.service.PositionService;
import com.finki.timesheets.service.ProjectService;
import com.finki.timesheets.service.ReportService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final PositionService positionService;
    private ReportService reportService;

    @Autowired
    public ProjectController(ProjectService projectService, PositionService positionService, ReportService reportService) {
        this.projectService = projectService;
        this.positionService = positionService;
        this.reportService = reportService;
    }

    @GetMapping
    public ApiResponse<List<Project>> listProjects() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Project list fetched successfully.", projectService.findAll());
    }

    @GetMapping("/reports")
    public List<ProjectTotalSalary> getDetailedReport() {
        List<Project> projects = projectService.findAll();
        return this.reportService.calculateTotalSalaryByProject(projects);
    }

    @GetMapping("/{id}")
    public ApiResponse<Project> findProjectById(@PathVariable Long id) throws NotFoundException {
        return new ApiResponse<>(HttpStatus.OK.value(), "Project list fetched successfully.", projectService.findById(id));
    }

    @PostMapping
    public ApiResponse<Project> saveProject(@RequestBody ProjectPositionDto projectPosition) throws NotFoundException {
        Project project = projectService.save(projectPosition.getProject());
        this.positionService.saveOrUpdateAll(project, projectPosition.getPositions());
        return new ApiResponse<>(HttpStatus.OK.value(), "Project saved successfully.", project);
    }

    @PutMapping("/{id}")
    public ApiResponse<Project> update(@RequestBody ProjectPositionDto projectPosition) throws NotFoundException {

        Project project = projectService.update(projectPosition.getProject());
        this.positionService.saveOrUpdateAll(project, projectPosition.getPositions());
        return new ApiResponse<>(HttpStatus.OK.value(), "Project updated successfully.", project);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Project fetched successfully.", null);
    }
}
