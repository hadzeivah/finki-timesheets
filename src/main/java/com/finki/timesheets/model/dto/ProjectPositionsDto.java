package com.finki.timesheets.model.dto;

import com.finki.timesheets.model.Project;

import java.util.List;

;


public class ProjectPositionsDto {
    private Project project;
    private List<PositionSalaryDto> positions;

    public ProjectPositionsDto() {
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
}
