package com.finki.timesheets.controller;


import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.ProjectPosition;
import com.finki.timesheets.model.dto.ProjectMemberDto;
import com.finki.timesheets.model.dto.ProjectPositionDto;
import com.finki.timesheets.service.PositionSalaryService;
import com.finki.timesheets.service.PositionService;
import com.finki.timesheets.service.ProjectService;
import com.finki.timesheets.service.TimesheetService;
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
    private final TimesheetService timesheetService;
    private final PositionSalaryService positionSalaryService;

    @Autowired
    public ProjectController(ProjectService projectService, PositionService positionService, TimesheetService timesheetService,
                             PositionSalaryService positionSalaryService) {
        this.projectService = projectService;
        this.positionService = positionService;
        this.timesheetService = timesheetService;
        this.positionSalaryService = positionSalaryService;
    }

    @GetMapping
    public ApiResponse<List<Project>> listProjects() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Project list fetched successfully.", projectService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Project> findProjectById(@PathVariable Long id) throws NotFoundException {
        return new ApiResponse<>(HttpStatus.OK.value(), "Project list fetched successfully.", projectService.findById(id));
    }

    @PostMapping
    public ApiResponse<Project> saveProject(@RequestBody ProjectPositionDto projectPosition) throws NotFoundException {
        Project project = projectService.save(projectPosition.getProject());
        this.positionSalaryService.saveOrUpdateAll(project, projectPosition.getPositions());
        return new ApiResponse<>(HttpStatus.OK.value(), "Project saved successfully.", project);
    }

    @PostMapping("/assignMember")
    public ApiResponse<Project> assignMemberToProject(@RequestBody ProjectMemberDto projectMember) {

        Position position = this.positionService.findPositionByType(projectMember.getPositionType().name());
        ProjectPosition positionSalary = this.positionSalaryService.findByProjectAndPosition(projectMember.getProject(), position);
        this.timesheetService.save(projectMember.getProject(), projectMember.getMember(), positionSalary);
        return new ApiResponse<>(HttpStatus.OK.value(), "Member assigned to project successfully.", null);
    }


    @PutMapping("/{id}")
    public ApiResponse<Project> update(@RequestBody ProjectPositionDto projectPosition) throws NotFoundException {

        Project project = projectService.update(projectPosition.getProject());
        this.positionSalaryService.saveOrUpdateAll(project, projectPosition.getPositions());
        return new ApiResponse<>(HttpStatus.OK.value(), "Project updated successfully.", project);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Project fetched successfully.", null);
    }
}
