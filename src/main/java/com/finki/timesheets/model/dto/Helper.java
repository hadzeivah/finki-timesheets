package com.finki.timesheets.model.dto;

import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.ProjectPosition;

public class Helper {

    public static ProjectPosition positionFromDTO(PositionDto positionDto, Position position, Project project) {
        return new ProjectPosition(
                new ProjectPositionKey(position.getId(), project.getId()),
                project,
                position,
                positionDto.getSalary());
    }
}
