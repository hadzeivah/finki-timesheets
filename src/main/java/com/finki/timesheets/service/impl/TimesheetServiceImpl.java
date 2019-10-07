package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.*;
import com.finki.timesheets.repository.TimesheetRepository;
import com.finki.timesheets.service.ItemService;
import com.finki.timesheets.service.TimesheetService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "timesheetService")
public class TimesheetServiceImpl implements TimesheetService {

    private final TimesheetRepository timesheetRepository;
    private ItemService itemService;

    @Autowired
    public TimesheetServiceImpl(TimesheetRepository timesheetRepository, ItemService itemService) {
        this.timesheetRepository = timesheetRepository;
        this.itemService = itemService;
    }

    @Override
    public List<Timesheet> findTimesheetsByProject(Long projectId) {
        return timesheetRepository.findTimesheetByProjectId(projectId);
    }

    @Override
    public Long calculateTotalHoursSpentByTimesheet(Timesheet timesheet) {

        List<Item> items = itemService.findItemsByTimesheet(timesheet.getId());

        if (items.isEmpty())
            return 0L;
        else
            return items.stream().mapToLong(Item::getHours).sum();
    }

    @Override
    public void save(Project project, Member member, ProjectPosition positionSalary) {
        Timesheet newTimesheet = new Timesheet(project, member);
        newTimesheet.setPositionSalary(positionSalary);
        timesheetRepository.save(newTimesheet);
    }

    @Override
    public List<Timesheet> findAll() {
        return timesheetRepository.findAll();
    }

    @Override
    public Timesheet findTimesheetByProjectIdAndMemberId(Long projectId, Long memberId) throws NotFoundException {
        return timesheetRepository.findTimesheetByProjectIdAndMemberId(projectId, memberId).orElseThrow(() -> new NotFoundException("Timesheet not found"));
    }
}
