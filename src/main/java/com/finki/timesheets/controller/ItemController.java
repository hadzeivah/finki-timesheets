package com.finki.timesheets.controller;


import com.finki.timesheets.model.Item;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.repository.ItemRepository;
import com.finki.timesheets.repository.TimesheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemRepository itemRepository;
    private final TimesheetRepository timesheetRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository, TimesheetRepository timesheetRepository) {
        this.itemRepository = itemRepository;
        this.timesheetRepository = timesheetRepository;
    }

    @GetMapping(params = {"timesheetId"})
    public Optional<List<Item>> findItemsByTimesheetId(@RequestParam("timesheetId") Long timesheetId) {
        Optional<Timesheet> timesheet = timesheetRepository.findById(timesheetId);
        return itemRepository.findItemsByTimesheet(timesheet.orElse(new Timesheet()));
    }
}
