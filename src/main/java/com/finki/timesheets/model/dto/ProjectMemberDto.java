package com.finki.timesheets.model.dto;

import com.finki.timesheets.model.Member;
import com.finki.timesheets.model.PositionType;
import com.finki.timesheets.model.Project;

public class ProjectMemberDto {
    private Project project;
    private Member member;
    private PositionType positionType;

    public ProjectMemberDto(Project project, Member member, PositionType position) {
        this.project = project;
        this.member = member;
        this.positionType = position;
    }

    public PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
