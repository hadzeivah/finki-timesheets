package com.finki.timesheets.service;

import com.finki.timesheets.model.Member;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.Timesheet;

import java.util.List;

public interface TimesheetService {

    List<Timesheet> findTimesheetsByProject(Long projectId);

    Long calculateTotalHoursSpentByTimesheet(Timesheet timesheet);

    void save(Project project , Member member);
}
