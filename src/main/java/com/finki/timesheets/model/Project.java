package com.finki.timesheets.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String projectNumber;

    @Column
    private String partnerOrganisation;

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private List<Timesheet> timesheets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Timesheet> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(List<Timesheet> timesheets) {
        this.timesheets = timesheets;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getPartnerOrganisation() {
        return partnerOrganisation;
    }
}
