package com.finki.timesheets.controller;


import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Member;
import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.dto.MemberDto;
import com.finki.timesheets.service.MemberService;
import com.finki.timesheets.service.ProjectService;
import com.finki.timesheets.service.TimesheetService;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/members")
public class MemberController {

    private static final long serialVersionUID = 4865903039190150223L;
    private final MemberService memberService;
    private ProjectService projectService;
    private TimesheetService timesheetService;

    @Autowired
    public MemberController(MemberService memberService, ProjectService projectService, TimesheetService timesheetService) {
        this.memberService = memberService;
        this.projectService = projectService;
        this.timesheetService = timesheetService;
    }

    @GetMapping
    public ApiResponse<List<Member>> listMembers() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Members list fetched successfully.", memberService.findAll());
    }

    @PostMapping()
    public ApiResponse<Member> saveItem(@RequestBody MemberDto member) {

        Member newMember = new Member();
        BeanUtils.copyProperties(member,newMember);
        Member savedMember = memberService.save(newMember);

        member.getProjects().forEach(project -> timesheetService.save(project, savedMember));
        return new ApiResponse<>(HttpStatus.OK.value(), "Member saved successfully.", savedMember);
    }

    @PutMapping("/{id}")
    public ApiResponse<Member> update(@RequestBody Member member) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Member updated successfully.", memberService.update(member));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Member fetched successfully.", null);
    }

    @GetMapping("positions")
    public List<Position> memberTypes() {
        return Arrays.asList(Position.values());
    }
}
