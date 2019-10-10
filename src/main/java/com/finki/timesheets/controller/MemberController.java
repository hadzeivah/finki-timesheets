package com.finki.timesheets.controller;


import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Member;
import com.finki.timesheets.model.PositionType;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.model.dto.MemberProjectsDto;
import com.finki.timesheets.model.dto.ProjectPosition;
import com.finki.timesheets.service.MemberService;
import com.finki.timesheets.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final TimesheetService timesheetService;

    @Autowired
    public MemberController(MemberService memberService, TimesheetService timesheetService) {
        this.memberService = memberService;
        this.timesheetService = timesheetService;
    }

    @GetMapping
    public ApiResponse<List<Member>> getMembers() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Members list fetched successfully.", memberService.findAll());
    }

    @GetMapping("/{id}")
    public Member findMemberById(@PathVariable Long id) {
        return this.memberService.findById(id);
    }

    @PostMapping()
    public ApiResponse<Member> saveMember(@RequestBody Member member) {

        Member savedMember = memberService.save(member);

        return new ApiResponse<>(HttpStatus.OK.value(), "Member saved successfully.", savedMember);
    }

    @PutMapping("/{id}")
    public ApiResponse<Member> update(@RequestBody Member member) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Member updated successfully.", memberService.update(member));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {

        Member member = this.memberService.findById(id);
        member.setDeleted(true);
        memberService.update(member);
        return new ApiResponse<>(HttpStatus.OK.value(), "Member fetched successfully.", null);
    }

    @GetMapping("/positions")
    public List<PositionType> memberTypes() {
        return Arrays.asList(PositionType.values());
    }

    @GetMapping("/details")
    public List<MemberProjectsDto> getMembersDetails() {
        List<Member> members = this.memberService.findAll();
        List<MemberProjectsDto> memberProjects = new ArrayList<>();

        members.forEach(member -> {
            Set<Timesheet> timesheets = member.getTimesheets();
            List<ProjectPosition> projectPositions = new ArrayList<>();
            timesheets.forEach(timesheet ->
            {
                projectPositions.add(new ProjectPosition(timesheet.getProject().getId(), timesheet.getProject().getName(), timesheet.getPositionSalary().getPosition().getName()));

            });
            MemberProjectsDto memberProjectsDto = new MemberProjectsDto(member, projectPositions);
            memberProjects.add(memberProjectsDto);

        });

        return memberProjects;
    }

}
