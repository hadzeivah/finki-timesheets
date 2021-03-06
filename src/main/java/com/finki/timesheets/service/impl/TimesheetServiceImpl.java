package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.*;
import com.finki.timesheets.repository.TimesheetRepository;
import com.finki.timesheets.service.TimesheetService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "timesheetService")
public class TimesheetServiceImpl implements TimesheetService {

    private final TimesheetRepository timesheetRepository;

    @Autowired
    public TimesheetServiceImpl(TimesheetRepository timesheetRepository) {
        this.timesheetRepository = timesheetRepository;
    }

    @Override
    public List<Timesheet> findTimesheetsByProject(Long projectId) {
        return timesheetRepository.findTimesheetByProjectId(projectId);
    }

    @Override
    public void save(Project project, Member member, ProjectPosition projectPosition) {
        Timesheet newTimesheet = new Timesheet(project, member);
        newTimesheet.setProjectPosition(projectPosition);
        timesheetRepository.save(newTimesheet);
    }

    @Override
    public List<Timesheet> findAll() {
        return timesheetRepository.findAll();
    }

    @Override
    public Timesheet findById(Long id) throws NotFoundException {
        return this.timesheetRepository.findById(id).orElseThrow(() -> new NotFoundException("Timesheet not found"));
    }

    @Override
    public Timesheet findTimesheetByProjectIdAndMemberId(Long projectId, Long memberId) throws NotFoundException {
        return timesheetRepository.findTimesheetByProjectIdAndMemberId(projectId, memberId).orElseThrow(() -> new NotFoundException("Timesheet not found"));
    }

    @Override
    public ApiResponse delete(Long memberId, Long projectId) throws NotFoundException {

        Timesheet timesheet = timesheetRepository.findTimesheetByProjectIdAndMemberId(projectId, memberId).orElseThrow(() -> new NotFoundException("Timesheet not found"));

        if (timesheet.getItems().isEmpty()) {
            timesheetRepository.delete(timesheet);
            return new ApiResponse<>(HttpStatus.OK.value(), "Timesheet successfully deleted", null);
        } else
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "Action is not successful. Remove items for hard delete", null);

    }
}
