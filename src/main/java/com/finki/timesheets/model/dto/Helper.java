package com.finki.timesheets.model.dto;

import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.ProjectPosition;

public class Helper {

    public static ProjectPosition positionFromDTO(PositionSalaryDto positionSalaryDto, Position position, Project project) {
        return new ProjectPosition(
                project,
                position,
                positionSalaryDto.getSalary());
    }
}
