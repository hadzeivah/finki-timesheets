package com.finki.timesheets.controller;

import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.model.WorkingHoursSummary;
import com.finki.timesheets.service.TimesheetService;
import com.finki.timesheets.service.WorkingHoursSummaryByMemberService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/timesheets")
public class TimesheetController {

    private final TimesheetService timesheetService;
    private WorkingHoursSummaryByMemberService workingHoursSummaryByMemberService;

    @Autowired
    public TimesheetController(TimesheetService timesheetService, WorkingHoursSummaryByMemberService workingHoursSummaryByMemberService) {
        this.timesheetService = timesheetService;
        this.workingHoursSummaryByMemberService = workingHoursSummaryByMemberService;
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

    @GetMapping("/working_hours_summary/member/{memberId}")
    public Map<LocalDate, WorkingHoursSummary> findWorkingHoursSummaryByMember(@PathVariable Long memberId) {
        return workingHoursSummaryByMemberService.findByMemberId(memberId).stream().collect(Collectors.toMap(WorkingHoursSummary::getDate, Function.identity()));
    }
}
