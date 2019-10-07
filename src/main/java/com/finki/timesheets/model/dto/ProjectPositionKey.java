package com.finki.timesheets.model.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class ProjectPositionKey implements Serializable {

    @Column(name = "position_id")
    private Long positionId;

    @Column(name = "project_id")
    private Long projectId;

    public ProjectPositionKey() {
    }

    public ProjectPositionKey(Long positionId, Long projectId) {
        this.positionId = positionId;
        this.projectId = projectId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectPositionKey that = (ProjectPositionKey) o;
        return Objects.equals(positionId, that.positionId) &&
                Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionId, projectId);
    }
}
