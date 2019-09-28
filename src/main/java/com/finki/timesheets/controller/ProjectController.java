package com.finki.timesheets.controller;


import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Item;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.model.dto.MemberTotalSalary;
import com.finki.timesheets.model.dto.ProjectPositionDto;
import com.finki.timesheets.model.dto.ProjectTotalSalary;
import com.finki.timesheets.service.PositionService;
import com.finki.timesheets.service.ProjectService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final PositionService positionService;

    @Autowired
    public ProjectController(ProjectService projectService, PositionService positionService) {
        this.projectService = projectService;
        this.positionService = positionService;
    }

    @GetMapping
    public ApiResponse<List<Project>> listProjects() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Project list fetched successfully.", projectService.findAll());
    }

    @GetMapping("/reports")
    public List<ProjectTotalSalary> getDetailedReport() {
        List<Project> projects = projectService.findAll();
        List<ProjectTotalSalary> projectTotalSalaries = new ArrayList<>();

        projects.forEach(project -> {
            Set<Timesheet> timesheets = project.getTimesheets();
            List<MemberTotalSalary> memberTotalSalaries = new ArrayList<>();
            timesheets.forEach(timesheet -> {
                double total = (timesheet.getItems().stream().mapToLong(Item::getHours).sum() / 24.0) * timesheet.getPositionSalary().getSalary();
                memberTotalSalaries.add(new MemberTotalSalary(timesheet.getMember().getFullName(), total));
            });
            projectTotalSalaries.add(new ProjectTotalSalary(project.getName(), 60L, memberTotalSalaries.stream().mapToDouble(MemberTotalSalary::getTotalSalary).sum(), memberTotalSalaries));
        });

        return projectTotalSalaries;
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
