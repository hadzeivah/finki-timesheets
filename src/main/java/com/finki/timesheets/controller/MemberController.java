package com.finki.timesheets.controller;


import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Member;
import com.finki.timesheets.model.Member;
import com.finki.timesheets.service.MemberService;
import com.finki.timesheets.service.MemberService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService projectService;

    @Autowired
    public MemberController(MemberService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ApiResponse<List<Member>> listMembers(){
        return new ApiResponse<>(HttpStatus.OK.value(), "Members list fetched successfully.",projectService.findAll());
    }

    @PostMapping
    public ApiResponse<Member> saveItem(@RequestBody Member project) throws NotFoundException {
        return new ApiResponse<>(HttpStatus.OK.value(), "Member saved successfully.", projectService.save(project));
    }

    @PutMapping("/{id}")
    public ApiResponse<Member> update(@RequestBody Member project) throws NotFoundException {
        return new ApiResponse<>(HttpStatus.OK.value(), "Member updated successfully.", projectService.update(project));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Member fetched successfully.", null);
    }
}
