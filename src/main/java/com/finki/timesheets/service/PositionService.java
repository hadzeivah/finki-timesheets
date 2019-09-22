package com.finki.timesheets.service;

import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.PositionSalary;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.dto.PositionDto;

import java.util.List;
import java.util.Map;

public interface PositionService {

    Map<String,Integer> getAllPositionsAndSalaryMapByProject(Long id);
    List<Position> findAll();
    List<PositionSalary> saveOrUpdateAll(Project project, List<PositionDto> positions);
    List<PositionSalary> findAllByProjectId(Long id);
}
