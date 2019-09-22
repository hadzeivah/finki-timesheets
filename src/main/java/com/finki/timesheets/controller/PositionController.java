package com.finki.timesheets.controller;

import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.PositionSalary;
import com.finki.timesheets.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/positions")
public class PositionController {

    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("/salaries/map/project/{id}")
    public Map<String, Integer> getProjectPositionsSalariesAsMap(@PathVariable Long id) {
        return this.positionService.getAllPositionsAndSalaryMapByProject(id);
    }

    @GetMapping("/salaries/project/{id}")
    public List<PositionSalary> getProjectPositionsSalaries(@PathVariable Long id) {
        return this.positionService.findAllByProjectId(id);
    }

    @GetMapping()
    public List<Position> getAllPositions() {
        return this.positionService.findAll();
    }

}
