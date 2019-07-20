package com.finki.timesheets.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "timesheets")
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime fromPeriod;
    @Column
    private LocalDateTime toPeriod;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    public Timesheet() {
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

    public LocalDateTime getFromPeriod() {
        return fromPeriod;
    }

    public void setFromPeriod(LocalDateTime fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public LocalDateTime getToPeriod() {
        return toPeriod;
    }

    public void setToPeriod(LocalDateTime toPeriod) {
        this.toPeriod = toPeriod;
    }
}
