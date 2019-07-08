package com.finki.timesheets.controller;


import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Member;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.service.MemberService;
import com.finki.timesheets.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ApiResponse<List<Member>> listMembers(){
        return new ApiResponse<>(HttpStatus.OK.value(), "Members list fetched successfully.",memberService.findAll());
    }
}
