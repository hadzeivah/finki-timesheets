package com.finki.timesheets.service.impl;


import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.ProjectPosition;
import com.finki.timesheets.repository.PositionRepository;
import com.finki.timesheets.repository.PositionSalaryRepository;
import com.finki.timesheets.service.PositionService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service(value = "positionService")
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final PositionSalaryRepository positionSalaryRepository;


    public PositionServiceImpl(PositionRepository positionRepository, PositionSalaryRepository positionSalaryRepository) {
        this.positionRepository = positionRepository;
        this.positionSalaryRepository = positionSalaryRepository;
    }

    @Override
    public Map<String, Integer> getAllPositionsAndSalaryMapByProject(Long id) {
        return this.positionSalaryRepository.findAllByProjectId(id).stream().collect(Collectors.toMap(p -> p.getPosition().getName(), ProjectPosition::getSalary));
    }

    @Override
    public List<Position> findAll() {
        return this.positionRepository.findAll();
    }

    @Override
    public Position findPositionByType(String positionType) {
        return this.positionRepository.findPositionByName(positionType);
    }

    @Override
    public Position findById(Long id) throws NotFoundException {
        return this.positionRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found"));
    }
}

