package com.finki.timesheets.controller;


import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Member;
import com.finki.timesheets.model.Position;
import com.finki.timesheets.service.MemberService;
import javassist.NotFoundException;
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

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ApiResponse<List<Member>> listMembers() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Members list fetched successfully.", memberService.findAll());
    }

    @PostMapping
    public ApiResponse<Member> saveItem(@RequestBody Member member) throws NotFoundException {
        return new ApiResponse<>(HttpStatus.OK.value(), "Member saved successfully.", memberService.save(member));
    }

    @PutMapping("/{id}")
    public ApiResponse<Member> update(@RequestBody Member member) throws NotFoundException {
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
