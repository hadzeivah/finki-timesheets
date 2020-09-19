package com.finki.timesheets.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import java.time.LocalDate;

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

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate date;

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

    public Item(Timesheet timesheet, LocalDate date, int hours, Task taskDescription, Output intellectualOutput) {
        this.timesheet = timesheet;
        this.date = date;
        this.hours = hours;
        this.taskDescription = taskDescription;
        this.intellectualOutput = intellectualOutput;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
