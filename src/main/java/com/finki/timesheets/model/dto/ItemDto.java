package com.finki.timesheets.model.dto;

import java.time.LocalDateTime;

public class ItemDto {
    private Long id;
    private Long timesheetId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int hours;
    private String taskDescription;
    private String intellectualOutput;

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

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getIntellectualOutput() {
        return intellectualOutput;
    }

    public void setIntellectualOutput(String intellectualOutput) {
        this.intellectualOutput = intellectualOutput;
    }
}
