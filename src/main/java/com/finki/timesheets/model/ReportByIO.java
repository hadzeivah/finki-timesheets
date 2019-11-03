package com.finki.timesheets.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "report_total_by_intellectual_output")
public class ReportByIO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column
    private Long projectId;

    @Column
    private String intellectualOutput;

    @Column
    private String projectName;

    @Column
    private Long total;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getIntellectualOutput() {
        return intellectualOutput;
    }

    public void setIntellectualOutput(String intellectualOutput) {
        this.intellectualOutput = intellectualOutput;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
