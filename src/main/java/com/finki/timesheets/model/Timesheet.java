package com.finki.timesheets.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "timesheets")
public class Timesheet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "timesheet")
    private Set<Item> items;


    @ManyToOne
    @JoinColumn(name = "project_position", referencedColumnName = "id")
    private ProjectPosition projectPosition;


    @Column
    private Boolean isDeleted = false;

    public Timesheet() {
    }

    public Timesheet(Project project, Member member) {
        this.project = project;
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public ProjectPosition getProjectPosition() {
        return projectPosition;
    }

    public void setProjectPosition(ProjectPosition projectPosition) {
        this.projectPosition = projectPosition;
    }
}
