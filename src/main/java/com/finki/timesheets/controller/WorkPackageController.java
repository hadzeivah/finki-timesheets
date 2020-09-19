package com.finki.timesheets.controller;

import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Output;
import com.finki.timesheets.model.Task;
import com.finki.timesheets.model.WorkPackage;
import com.finki.timesheets.service.OutputService;
import com.finki.timesheets.service.TaskService;
import com.finki.timesheets.service.WorkPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/work_packages")
public class WorkPackageController {

    private final WorkPackageService workPackageService;
    private final TaskService taskService;
    private final OutputService outputService;

    @Autowired
    public WorkPackageController(WorkPackageService workPackageService, TaskService taskService, OutputService outputService) {
        this.workPackageService = workPackageService;
        this.taskService = taskService;
        this.outputService = outputService;
    }

    @GetMapping
    public List<WorkPackage> getAllWorkPackages() {
        return this.workPackageService.findAll();
    }

    @PostMapping
    public WorkPackage createWorkPackage(@RequestBody WorkPackage workPackage) {

        return this.workPackageService.save(workPackage);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.workPackageService.delete(id);
    }

    @PostMapping("/tasks")
    public ApiResponse<Task> saveTasks(@RequestBody WorkPackage workPackage) {

        workPackage.getTasks().forEach(task -> {
            task.setWorkPackage(workPackage);
        });

        return new ApiResponse<>(HttpStatus.OK.value(), "Tasks saved successfully.", this.taskService.saveAll(
                new ArrayList<>(workPackage.getTasks())));
    }

    @PostMapping("/outputs")
    public ApiResponse<Output> saveOutputs(@RequestBody WorkPackage workPackage) {
        workPackage.getOutputs().forEach(outputs -> {
            outputs.setWorkPackage(workPackage);
        });
        return new ApiResponse<>(HttpStatus.OK.value(), "Outputs saved successfully.", this.outputService.saveAll(new ArrayList<>(workPackage.getOutputs())));
    }


    @DeleteMapping("/outputs/{id}")
    public ApiResponse<Void> deleteOutput(@PathVariable Long id) {
        outputService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Output fetched successfully.", null);
    }

    @DeleteMapping("/tasks/{id}")
    public ApiResponse<Void> deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Task fetched successfully.", null);
    }
}
