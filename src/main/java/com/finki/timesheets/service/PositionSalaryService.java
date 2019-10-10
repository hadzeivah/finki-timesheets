package com.finki.timesheets.service;

import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.ProjectPosition;
import com.finki.timesheets.model.dto.PositionSalaryDto;

import java.util.List;

public interface PositionSalaryService {
    List<ProjectPosition> saveOrUpdateAll(Project project, List<PositionSalaryDto> positions);

    List<ProjectPosition> findAllByProjectId(Long id);

    ProjectPosition findByProjectAndPosition(Project project, Position position);
}
