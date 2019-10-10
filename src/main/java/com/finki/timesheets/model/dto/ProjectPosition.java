package com.finki.timesheets.model.dto;


public class ProjectPosition {
    private Long projectId;
    private String projectName;
    private String positionName;

    public ProjectPosition(Long projectId, String projectName, String positionName) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.positionName = positionName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
