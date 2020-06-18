package com.finki.timesheets.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
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

    @Column
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate endDate;

    @Column
    @Pattern(regexp = "[0-8]")
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
