package com.finki.timesheets.model.dto;

import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.PositionSalary;
import com.finki.timesheets.model.Project;

public class Helper {

    public static PositionSalary positionFromDTO(PositionDto positionDto, Position position , Project project) {
        return new PositionSalary(
                new PositionSalaryKey(position.getId(), project.getId()),
                project,
                position,
                positionDto.getSalary());
    }
}
