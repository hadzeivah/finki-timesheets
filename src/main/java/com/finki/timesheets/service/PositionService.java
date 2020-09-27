package com.finki.timesheets.service;

import com.finki.timesheets.model.Position;
import javassist.NotFoundException;

import java.util.List;
import java.util.Map;

public interface PositionService {

    Map<String,Integer> getAllPositionsAndSalaryMapByProject(Long id);
    List<Position> findAll();

    Position findPositionByType(String positionType);

    Position findById(Long id) throws NotFoundException;
}
