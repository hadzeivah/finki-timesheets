package com.finki.timesheets.model.dto;

import com.finki.timesheets.model.Output;
import com.finki.timesheets.model.Task;

import java.time.LocalDate;

public class ItemDto {
    private Long id;
    private Long timesheetId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int hours;
    private Task taskDescription;
    private Output intellectualOutput;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(Long timesheetId) {
        this.timesheetId = timesheetId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
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
