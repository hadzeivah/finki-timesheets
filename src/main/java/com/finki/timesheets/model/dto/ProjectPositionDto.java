package com.finki.timesheets.model.dto;;
import com.finki.timesheets.model.Project;

import java.util.List;


public class ProjectPositionDto {
    private Project project;
    private List<PositionDto> positions;

    public ProjectPositionDto() {
    }


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


    public List<PositionDto> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionDto> positions) {
        this.positions = positions;
    }
}
