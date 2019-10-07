package com.finki.timesheets.service.impl;


import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.ProjectPosition;
import com.finki.timesheets.model.dto.Helper;
import com.finki.timesheets.model.dto.PositionDto;
import com.finki.timesheets.repository.PositionRepository;
import com.finki.timesheets.repository.PositionSalaryRepository;
import com.finki.timesheets.service.PositionSalaryService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service(value = "positionSalaryService")
public class PositionSalaryServiceImpl implements PositionSalaryService {

    private final PositionRepository positionRepository;
    private final PositionSalaryRepository positionSalaryRepository;


    public PositionSalaryServiceImpl(PositionRepository positionRepository, PositionSalaryRepository positionSalaryRepository) {
        this.positionRepository = positionRepository;
        this.positionSalaryRepository = positionSalaryRepository;
    }

    @Override
    public List<ProjectPosition> findAllByProjectId(Long id) {
        return this.positionSalaryRepository.findAllByProjectId(id);
    }

    @Override
    public ProjectPosition findByProjectAndPosition(Project project, Position position) {
        return this.positionSalaryRepository.findByProjectAndAndPosition(project, position);
    }

    @Override
    @Transactional
    public List<ProjectPosition> saveOrUpdateAll(Project project, List<PositionDto> positions) {

        Map<String, Position> positionMap = this.positionRepository.findAll().stream().collect(Collectors.toMap(Position::getName, Function.identity()));

        List<ProjectPosition> positionSalaries = new ArrayList<>();
        positions.forEach(positionDto -> {
            Position position = positionMap.get(positionDto.getPositionType());
            if (position == null) {
                position = this.positionRepository.save(new Position(positionDto.getPositionType(), positionDto.getPositionType()));
            }
            ProjectPosition positionSalary = Helper.positionFromDTO(positionDto, position, project);
            positionSalaries.add(positionSalary);
        });

        return this.positionSalaryRepository.saveAll(positionSalaries);
    }

}

