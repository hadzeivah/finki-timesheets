package com.finki.timesheets.controller;


import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Member;
import com.finki.timesheets.model.PositionType;
import com.finki.timesheets.model.dto.MemberDto;
import com.finki.timesheets.service.MemberService;
import com.finki.timesheets.service.TimesheetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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
    public ApiResponse<List<Member>> listMembers() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Members list fetched successfully.", memberService.findAll());
    }

    @GetMapping("/{id}")
    public Member findMemberById(@PathVariable Long id) {
        return this.memberService.findById(id);
    }

    @PostMapping()
    public ApiResponse<Member> saveMember(@RequestBody MemberDto member) {

        Member newMember = new Member();
        BeanUtils.copyProperties(member,newMember);
        Member savedMember = memberService.save(newMember);

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
    public List<PositionType> memberTypes() {
        return Arrays.asList(PositionType.values());
    }
}
