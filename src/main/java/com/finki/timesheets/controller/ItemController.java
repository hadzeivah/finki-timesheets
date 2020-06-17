package com.finki.timesheets.controller;


import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Item;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.model.dto.ItemDto;
import com.finki.timesheets.service.ItemService;
import com.finki.timesheets.service.TimesheetService;
import com.finki.timesheets.service.utils.CsvUtils;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final TimesheetService timesheetService;

    @Autowired
    public ItemController(ItemService itemService, TimesheetService timesheetService) {
        this.itemService = itemService;
        this.timesheetService = timesheetService;
    }

    @GetMapping(params = {"timesheetId"})
    public List<Item> findItemsByTimesheetId(@RequestParam("timesheetId") Long timesheetId) {
        return itemService.findItemsByTimesheet(timesheetId);
    }

    @PostMapping
    public ApiResponse<Item> saveItem(@RequestBody ItemDto item) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Item saved successfully.", itemService.save(item));
    }

    @PutMapping("/{id}")
    public ApiResponse<ItemDto> update(@RequestBody ItemDto itemDto) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Item updated successfully.", itemService.update(itemDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        itemService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Item fetched successfully.", null);
    }

    @PostMapping(value = "/{id}/import", consumes = "multipart/form-data")
    public ResponseEntity<String> importTimesheetItems(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException, NotFoundException {
        List<Item> items = CsvUtils.read(Item.class, file.getInputStream());
        Timesheet timesheet = this.timesheetService.findById(id);

        AtomicInteger counter = new AtomicInteger();

        items.forEach(item -> {
            item.setTimesheet(timesheet);
            item.setStartDate(LocalDateTime.now().plusDays(counter.get()));
            item.setEndDate(LocalDateTime.now().plusDays(counter.get() + 1));
            item.setIntellectualOutput(timesheet.getItems().iterator().next().getIntellectualOutput());
            item.setTaskDescription(timesheet.getItems().iterator().next().getTaskDescription());
            counter.getAndIncrement();
        });

        this.itemService.saveAll(items);
        return new ResponseEntity<String>(file.getName(), HttpStatus.OK);
    }
}
