package com.finki.timesheets.controller;

import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Output;
import com.finki.timesheets.model.Task;
import com.finki.timesheets.model.WorkPackage;
import com.finki.timesheets.service.OutputService;
import com.finki.timesheets.service.TaskService;
import com.finki.timesheets.service.WorkPackageService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work_packages")
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

    @PostMapping("/{workPackageId}/task")
    public Task saveTask(@RequestBody Task task, @PathVariable Long workPackageId) throws NotFoundException {
        WorkPackage workPackage = this.workPackageService.findOne(workPackageId);
        task.setWorkPackage(workPackage);
        return this.taskService.save(task);
    }

    @PostMapping("/{workPackageId}/output")
    public Output saveOutput(@RequestBody Output output, @PathVariable Long workPackageId) throws NotFoundException {
        WorkPackage workPackage = this.workPackageService.findOne(workPackageId);
        output.setWorkPackage(workPackage);
        return this.outputService.save(output);
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
