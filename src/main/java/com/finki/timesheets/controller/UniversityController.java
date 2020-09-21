package com.finki.timesheets.controller;

import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.University;
import com.finki.timesheets.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    private final UniversityService universityService;

    @Autowired
    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping
    public ApiResponse<List<University>> listProjects() {
        return new ApiResponse<>(HttpStatus.OK.value(), "University list fetched successfully.", universityService.findAll());
    }
}
