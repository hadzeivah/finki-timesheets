package com.finki.timesheets.controller;

import com.finki.timesheets.model.WorkPackage;
import com.finki.timesheets.service.WorkPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/work_packages")
public class WorkPackageController {

    private final WorkPackageService workPackageService;

    @Autowired
    public WorkPackageController(WorkPackageService workPackageService) {
        this.workPackageService = workPackageService;
    }

    @GetMapping
    public List<WorkPackage> getAllWorkPackages() {
        return this.workPackageService.findAll();
    }
}
