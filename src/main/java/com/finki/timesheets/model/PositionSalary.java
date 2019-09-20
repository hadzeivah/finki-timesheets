package com.finki.timesheets.model;

import com.finki.timesheets.model.dto.PositionSalaryKey;

import javax.persistence.*;

@Entity
public class PositionSalary {
    @EmbeddedId
    private PositionSalaryKey id;

    @ManyToOne
    @MapsId("project_id")
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @MapsId("position_id")
    @JoinColumn(name = "position_id")
    private Position position;

    private int salary;

    public PositionSalary() {
    }

    public PositionSalary(PositionSalaryKey id, Project project, Position position, int salary) {
        this.id = id;
        this.project = project;
        this.position = position;
        this.salary = salary;
    }

    public PositionSalaryKey getId() {
        return id;
    }

    public void setId(PositionSalaryKey id) {
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
