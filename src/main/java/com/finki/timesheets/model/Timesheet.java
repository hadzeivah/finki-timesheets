package com.finki.timesheets.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "timesheets")
public class Timesheet {

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
    @JoinColumns({
            @JoinColumn(name = "position_id", insertable = false, updatable = false),
            @JoinColumn(name = "project_id", insertable = false, updatable = false)
    })
    private PositionSalary positionSalary;

    public Timesheet() {
    }

    public Timesheet(Project project , Member member) {
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

    public PositionSalary getPositionSalary() {
        return positionSalary;
    }

    public void setPositionSalary(PositionSalary positionSalary) {
        this.positionSalary = positionSalary;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
