package com.finki.timesheets.model.dto;

import com.finki.timesheets.model.Output;
import com.finki.timesheets.model.Task;

import java.time.LocalDateTime;

public class ItemDto {
    private Long id;
    private Long timesheetId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
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
