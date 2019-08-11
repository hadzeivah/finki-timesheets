package com.finki.timesheets.controller;


import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.service.ProjectService;
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

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ApiResponse<List<Project>> listProjects() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Project list fetched successfully.", projectService.findAll());
    }

    @PostMapping
    public ApiResponse<Project> saveItem(@RequestBody Project project) throws NotFoundException {
        return new ApiResponse<>(HttpStatus.OK.value(), "Project saved successfully.", projectService.save(project));
    }

    @PutMapping("/{id}")
    public ApiResponse<Project> update(@RequestBody Project project) throws NotFoundException {
        if (!projectService.findById(project.getId()).isPresent()) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Project with id" + project.getId() + "not found", null);
        }
        return new ApiResponse<>(HttpStatus.OK.value(), "Project updated successfully.", projectService.update(project));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Project fetched successfully.", null);
    }
}
