package com.finki.timesheets.controller;


import com.finki.timesheets.model.*;
import com.finki.timesheets.model.dto.ProjectMemberDto;
import com.finki.timesheets.model.dto.ProjectPositionsDto;
import com.finki.timesheets.service.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final PositionService positionService;
    private final TimesheetService timesheetService;
    private final ProjectPositionService projectPositionService;
    private UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService, PositionService positionService, TimesheetService timesheetService,
                             ProjectPositionService projectPositionService, UserService userService) {
        this.projectService = projectService;
        this.positionService = positionService;
        this.timesheetService = timesheetService;
        this.projectPositionService = projectPositionService;
        this.userService = userService;
    }

    @GetMapping
    public ApiResponse<List<Project>> getProjectsForLoggedUser(@AuthenticationPrincipal UserDetails currentUser) {
        User user = this.userService.findOne(currentUser.getUsername());
        return new ApiResponse<>(HttpStatus.OK.value(), "Project list fetched successfully.", projectService.findAllByProjectManagerIsDeletedFalse(user));
    }

    @GetMapping("/unapproved")
    public ApiResponse<List<Project>> getUnapprovedProjects() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Approved projects list fetched successfully.", projectService.findAllUnapprovedProjects());
    }

    @GetMapping("/{id}")
    public ApiResponse<Project> findProjectById(@PathVariable Long id) throws NotFoundException {
        return new ApiResponse<>(HttpStatus.OK.value(), "Project list fetched successfully.", projectService.findById(id));
    }

    @PostMapping
    public ApiResponse<Project> saveProject(@RequestBody ProjectPositionsDto projectPosition, @AuthenticationPrincipal UserDetails currentUser) throws NotFoundException {
        Project project = projectPosition.getProject();
        User user = (User) this.userService.findOne(currentUser.getUsername());
        project.setProjectManager(user);
        this.projectPositionService.saveOrUpdateAll(projectService.save(project), projectPosition.getPositions());
        return new ApiResponse<>(HttpStatus.OK.value(), "Project saved successfully.", project);
    }

    @PostMapping("/assignMember")
    public ApiResponse<Project> assignMemberToProject(@RequestBody ProjectMemberDto projectMember) {

        Position position = this.positionService.findPositionByType(projectMember.getPositionType().name());
        ProjectPosition positionSalary = this.projectPositionService.findByProjectAndPosition(projectMember.getProject(), position);
        this.timesheetService.save(projectMember.getProject(), projectMember.getMember(), positionSalary);
        return new ApiResponse<>(HttpStatus.OK.value(), "Member assigned to project successfully.", null);
    }


    @PutMapping("/{id}")
    public ApiResponse<Project> update(@RequestBody ProjectPositionsDto projectPosition) throws NotFoundException {

        Project project = projectService.update(projectPosition.getProject());
        this.projectPositionService.saveOrUpdateAll(project, projectPosition.getPositions());
        return new ApiResponse<>(HttpStatus.OK.value(), "Project updated successfully.", project);
    }

    @PutMapping("/{id}/approve")
    public ApiResponse<Project> approveProject(@PathVariable Long id) throws NotFoundException {

        Project project = projectService.findById(id);
        project.setApproved(true);
        projectService.save(project);
        return new ApiResponse<>(HttpStatus.OK.value(), "Project approved", project);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) throws NotFoundException {
        Project project = this.projectService.findById(id);
        project.setDeleted(true);
        this.projectService.update(project);
        return new ApiResponse<>(HttpStatus.OK.value(), "Project deleted successfully.", null);
    }
}
