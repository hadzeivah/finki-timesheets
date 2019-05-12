package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Item;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.repository.TimesheetRepository;
import com.finki.timesheets.service.ItemService;
import com.finki.timesheets.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        Optional<List<Item>> items = itemService.findItemsByTimesheet(timesheet.getId());

        return  items.get().stream().mapToLong(Item::getHours).sum();
    }
}
