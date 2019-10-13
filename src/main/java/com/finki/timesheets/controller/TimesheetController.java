package com.finki.timesheets.controller;

import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.service.TimesheetService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/timesheets")
public class TimesheetController {

    private final TimesheetService timesheetService;

    @Autowired
    public TimesheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @GetMapping
    public List<Timesheet> findAllTimesheets() {
        return timesheetService.findAll();
    }

    @GetMapping(params = {"projectId", "memberId"})
    public Timesheet findById(@RequestParam("projectId") Long projectId, @RequestParam("memberId") Long memberId) throws NotFoundException {
        return timesheetService.findTimesheetByProjectIdAndMemberId(projectId, memberId);
    }

    @DeleteMapping("/member/{memberId}/project/{projectId}")
    public ApiResponse deleteTimesheet(@PathVariable Long memberId, @PathVariable Long projectId) throws NotFoundException {

        return this.timesheetService.delete(memberId, projectId);

    }
}
