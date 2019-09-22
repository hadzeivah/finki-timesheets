package com.finki.timesheets.service.impl;


import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.PositionSalary;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.dto.Helper;
import com.finki.timesheets.model.dto.PositionDto;
import com.finki.timesheets.model.dto.PositionSalaryKey;
import com.finki.timesheets.repository.PositionRepository;
import com.finki.timesheets.repository.PositionSalaryRepository;
import com.finki.timesheets.service.PositionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public Map<String, Integer> getAllPositionsAndSalaryMapByProject(Long id) {
        return this.positionSalaryRepository.findAllByProjectId(id).stream().collect(Collectors.toMap(p -> p.getPosition().getName(), PositionSalary::getSalary));
    }
    @Override
    public List<PositionSalary> findAllByProjectId(Long id) {
        return this.positionSalaryRepository.findAllByProjectId(id);
    }
    @Override
    public List<Position> findAll() {
        return this.positionRepository.findAll();
    }

    @Override
    @Transactional
    public List<PositionSalary> saveOrUpdateAll(Project project, List<PositionDto> positions) {

        Map<String, Position> positionMap = findAll().stream().collect(Collectors.toMap(Position::getName, Function.identity()));

        List<PositionSalary> positionSalaries = new ArrayList<>();
        positions.forEach(positionDto -> {
            Position position = positionMap.get(positionDto.getPositionType());
            if (position == null) {
                position = this.positionRepository.save(new Position(positionDto.getPositionType(), positionDto.getPositionType()));
            }
            PositionSalary positionSalary = Helper.positionFromDTO(positionDto, position, project);
            positionSalaries.add(positionSalary);
        });

        return this.positionSalaryRepository.saveAll(positionSalaries);
    }
}

