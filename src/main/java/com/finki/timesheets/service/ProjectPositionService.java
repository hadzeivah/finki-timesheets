package com.finki.timesheets.service;

import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.ProjectPosition;
import com.finki.timesheets.model.dto.PositionSalaryDto;
import javassist.NotFoundException;

import java.util.List;

public interface ProjectPositionService {
    List<ProjectPosition> saveOrUpdateAll(Project project, List<PositionSalaryDto> positions);

    List<ProjectPosition> findAllByProjectId(Long id);

    ProjectPosition findByProjectAndPosition(Project project, Position position);

    ProjectPosition findById(Long id) throws NotFoundException;

}
