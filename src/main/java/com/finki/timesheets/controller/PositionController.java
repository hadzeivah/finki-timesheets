package com.finki.timesheets.controller;

import com.finki.timesheets.model.Position;
import com.finki.timesheets.service.PositionService;
import com.finki.timesheets.service.ProjectService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/positions")
public class PositionController {

    private final PositionService positionService;
    private final ProjectService projectService;

    @Autowired
    public PositionController(PositionService positionService, ProjectService projectService) {
        this.positionService = positionService;
        this.projectService = projectService;
    }

    @GetMapping("/project/{id}")
    public List<Position> getProjectPositions(@PathVariable Long id) throws NotFoundException {
        return new ArrayList<>();
    }

    @GetMapping("/salary")
    public Map<String, Integer> findPositionsAndSalary() {
        return this.positionService.getAllPositionsAndSalaryMap();
    }

    @GetMapping()
    public List<Position> getAllPositions() {
        return this.positionService.findAll();
    }

}
