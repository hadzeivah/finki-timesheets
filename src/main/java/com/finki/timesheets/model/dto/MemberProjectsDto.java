package com.finki.timesheets.model.dto;

import com.finki.timesheets.model.Member;

import java.util.List;

public class MemberProjectsDto {
    private Member member;
    private List<ProjectPosition> projectPosition;

    public MemberProjectsDto(Member member, List<ProjectPosition> projectPosition) {
        this.member = member;
        this.projectPosition = projectPosition;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<ProjectPosition> getProjectPosition() {
        return projectPosition;
    }

    public void setProjectPosition(List<ProjectPosition> projectPosition) {
        this.projectPosition = projectPosition;
    }
}
