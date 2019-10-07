package com.finki.timesheets.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finki.timesheets.model.dto.ProjectPositionKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ProjectPosition implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProjectPositionKey id;

    @JsonIgnore
    @ManyToOne
    @MapsId("project_id")
    @JoinColumn(name = "project_id")
    private Project project;

    @JsonIgnore
    @ManyToOne
    @MapsId("position_id")
    @JoinColumn(name = "position_id")
    private Position position;

    private int salary;

    public ProjectPosition() {
    }

    public ProjectPosition(ProjectPositionKey id, Project project, Position position, int salary) {
        this.id = id;
        this.project = project;
        this.position = position;
        this.salary = salary;
    }

    public ProjectPositionKey getId() {
        return id;
    }

    public void setId(ProjectPositionKey id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
