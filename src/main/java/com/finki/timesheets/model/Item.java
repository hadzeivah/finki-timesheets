package com.finki.timesheets.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "timesheet_id", referencedColumnName = "id")
    private Timesheet timesheet;

    @Column
    private LocalDateTime startDate = LocalDateTime.now();

    @Column
    private LocalDateTime endDate = LocalDateTime.now();

    @Column
    private Integer hours;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task taskDescription;

    @ManyToOne
    @JoinColumn(name = "output_id", referencedColumnName = "id")
    private Output intellectualOutput;

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timesheet getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Task getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(Task taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Output getIntellectualOutput() {
        return intellectualOutput;
    }

    public void setIntellectualOutput(Output intellectualOutput) {
        this.intellectualOutput = intellectualOutput;
    }
}
