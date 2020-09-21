package com.finki.timesheets.controller;

import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.ProjectPosition;
import com.finki.timesheets.service.PositionSalaryService;
import com.finki.timesheets.service.PositionService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    private final PositionService positionService;
    private PositionSalaryService positionSalaryService;

    @Autowired
    public PositionController(PositionService positionService, PositionSalaryService positionSalaryService) {
        this.positionService = positionService;
        this.positionSalaryService = positionSalaryService;
    }

    @GetMapping("/salaries/map/project/{id}")
    public Map<String, Integer> getProjectPositionsSalariesAsMap(@PathVariable Long id) {
        return this.positionService.getAllPositionsAndSalaryMapByProject(id);
    }

    @GetMapping("/salaries/project/{id}")
    public List<ProjectPosition> getProjectPositionsSalaries(@PathVariable Long id) {
        return this.positionSalaryService.findAllByProjectId(id);
    }

    @GetMapping()
    public List<Position> getAllPositions() {
        return this.positionService.findAll();
    }


    @GetMapping("/{id}")
    public Position getPosition(@PathVariable Long id) throws NotFoundException {
        return this.positionService.findById(id);
    }

}
