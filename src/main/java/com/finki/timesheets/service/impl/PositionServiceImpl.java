package com.finki.timesheets.service.impl;


import com.finki.timesheets.model.Position;
import com.finki.timesheets.repository.PositionRepository;
import com.finki.timesheets.service.PositionService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;


@Service(value = "positionService")
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public Map<String, Integer> getAllPositionsAndSalaryMap() {
        return this.positionRepository.findAll().stream().collect(Collectors.toMap(Position::getName, Position::getSalary));
    }
}

