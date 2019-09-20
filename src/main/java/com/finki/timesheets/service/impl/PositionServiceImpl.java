package com.finki.timesheets.service.impl;


import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.PositionSalary;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.dto.PositionDto;
import com.finki.timesheets.model.dto.PositionSalaryKey;
import com.finki.timesheets.repository.PositionRepository;
import com.finki.timesheets.repository.PositionSalaryRepository;
import com.finki.timesheets.service.PositionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
    public Map<String, Integer> getAllPositionsAndSalaryMap() {
        return this.positionRepository.findAll().stream().collect(Collectors.toMap(Position::getName, Position::getSalary));
    }

    @Override
    public List<Position> findAll() {
        return this.positionRepository.findAll();
    }

    @Override
    public List<PositionSalary> saveAll(Project project, List<PositionDto> positions) {

        Map<String , Position> positionMap = findAll().stream().collect(Collectors.toMap(Position::getName, Function.identity()));

        List<PositionSalary> positionSalaries = new ArrayList<>();
        positions.forEach(position -> {
            PositionSalary positionSalary = new PositionSalary(
                    new PositionSalaryKey(positionMap.get(position.getPositionType()).getId(),project.getId()),
                    project,
                    positionMap.get(position.getPositionType()),
                    position.getSalary());
            positionSalaries.add(positionSalary);
        });

        return this.positionSalaryRepository.saveAll(positionSalaries);
    }
}

