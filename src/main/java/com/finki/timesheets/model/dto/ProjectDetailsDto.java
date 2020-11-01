package com.finki.timesheets.model.dto;

import com.finki.timesheets.model.Project;

import java.util.List;


public class ProjectDetailsDto {
    private Project project;
    private List<PositionSalaryDto> positions;
    private List<PositionSalaryDto> positionsToDelete;

    public ProjectDetailsDto() {
    }


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


    public List<PositionSalaryDto> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionSalaryDto> positions) {
        this.positions = positions;
    }

    public List<PositionSalaryDto> getPositionsToDelete() {
        return positionsToDelete;
    }

    public void setPositionsToDelete(List<PositionSalaryDto> positionsToDelete) {
        this.positionsToDelete = positionsToDelete;
    }
}
