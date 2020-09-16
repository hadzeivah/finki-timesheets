package com.finki.timesheets.service;

import com.finki.timesheets.model.*;
import javassist.NotFoundException;

import java.util.List;

public interface TimesheetService {

    List<Timesheet> findTimesheetsByProject(Long projectId);

    void save(Project project, Member member, ProjectPosition positionSalary);

    List<Timesheet> findAll();

    Timesheet findById(Long id) throws NotFoundException;

    Timesheet findTimesheetByProjectIdAndMemberId(Long projectId, Long memberId) throws NotFoundException;

    ApiResponse delete(Long memberId, Long projectId) throws NotFoundException;
}
