package com.finki.timesheets.controller;


import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.repository.TimesheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/timesheets")
public class TimesheetController {

    private final TimesheetRepository timesheetRepository;

    @Autowired
    public TimesheetController(TimesheetRepository timesheetRepository) {
        this.timesheetRepository = timesheetRepository;
    }

    @GetMapping(params = {"projectId", "memberId"})
    public Optional<Timesheet> findById(@RequestParam("projectId") Long projectId,@RequestParam("memberId") Long memberId) {
        return timesheetRepository.findTimesheetByProjectIdAndMemberId(projectId, memberId);
    }
}
