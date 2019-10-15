package com.finki.timesheets.controller;


import com.finki.timesheets.model.*;
import com.finki.timesheets.model.dto.ProjectMemberDto;
import com.finki.timesheets.model.dto.ProjectPositionsDto;
import com.finki.timesheets.service.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final PositionService positionService;
    private final TimesheetService timesheetService;
    private final PositionSalaryService positionSalaryService;
    private UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService, PositionService positionService, TimesheetService timesheetService,
                             PositionSalaryService positionSalaryService, UserService userService) {
        this.projectService = projectService;
        this.positionService = positionService;
        this.timesheetService = timesheetService;
        this.positionSalaryService = positionSalaryService;
        this.userService = userService;
    }

    @GetMapping
    public ApiResponse<List<Project>> getProjects() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Project list fetched successfully.", projectService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Project> findProjectById(@PathVariable Long id) throws NotFoundException {
        return new ApiResponse<>(HttpStatus.OK.value(), "Project list fetched successfully.", projectService.findById(id));
    }

    @PostMapping
    public ApiResponse<Project> saveProject(@RequestBody ProjectPositionsDto projectPosition, Principal principal) throws NotFoundException {
        Project project = projectPosition.getProject();
        User user = userService.findOne(principal.getName());
        project.setProjectManager(user);
        this.positionSalaryService.saveOrUpdateAll(projectService.save(project), projectPosition.getPositions());
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
    public ApiResponse<Project> update(@RequestBody ProjectPositionsDto projectPosition) throws NotFoundException {

        Project project = projectService.update(projectPosition.getProject());
        this.positionSalaryService.saveOrUpdateAll(project, projectPosition.getPositions());
        return new ApiResponse<>(HttpStatus.OK.value(), "Project updated successfully.", project);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) throws NotFoundException {
        Project project = this.projectService.findById(id);
        project.setDeleted(true);
        this.projectService.update(project);
        return new ApiResponse<>(HttpStatus.OK.value(), "Project deleted successfully.", null);
    }
}
