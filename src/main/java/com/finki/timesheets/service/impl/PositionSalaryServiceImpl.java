package com.finki.timesheets.service.impl;


import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.ProjectPosition;
import com.finki.timesheets.model.dto.Helper;
import com.finki.timesheets.model.dto.PositionSalaryDto;
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
        return this.positionSalaryRepository.findByProjectAndPosition(project, position);
    }

    @Override
    @Transactional
    public List<ProjectPosition> saveOrUpdateAll(Project project, List<PositionSalaryDto> positions) {

        Map<String, Position> positionMap = this.positionRepository.findAll().stream().collect(Collectors.toMap(Position::getName, Function.identity()));
        Map<String, ProjectPosition> projectPositionMap = this.positionSalaryRepository.findAllByProjectId(project.getId()).stream().collect(Collectors.toMap(projectPosition -> projectPosition.getPosition().getName(), projectPosition -> projectPosition));

        List<ProjectPosition> projectPositions = new ArrayList<>();
        positions.forEach(positionSalaryDto -> {
            Position position = positionMap.get(positionSalaryDto.getPositionType());
            if (position == null) {
                position = this.positionRepository.save(new Position(positionSalaryDto.getPositionType(), positionSalaryDto.getPositionType()));
            }
            // if already exists
            ProjectPosition projectPosition = projectPositionMap.get(position.getName());
            if (projectPosition != null) {
                projectPosition.setSalary(positionSalaryDto.getSalary());
                projectPositions.add(projectPosition);
            } else {
                ProjectPosition positionSalary = Helper.positionFromDTO(positionSalaryDto, position, project);
                projectPositions.add(positionSalary);
            }

        });

        return this.positionSalaryRepository.saveAll(projectPositions);
    }

}

