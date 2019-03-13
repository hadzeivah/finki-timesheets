package com.finki.timesheets.controller;


import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Item;
import com.finki.timesheets.model.dto.ItemDto;
import com.finki.timesheets.model.dto.UserDto;
import com.finki.timesheets.repository.TimesheetRepository;
import com.finki.timesheets.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final TimesheetRepository timesheetRepository;

    @Autowired
    public ItemController(ItemService itemService, TimesheetRepository timesheetRepository) {
        this.itemService = itemService;
        this.timesheetRepository = timesheetRepository;
    }

    @GetMapping(params = {"timesheetId"})
    public Optional<List<Item>> findItemsByTimesheetId(@RequestParam("timesheetId") Long timesheetId) {
        return itemService.findItemsByTimesheet(timesheetId);
    }

    @PostMapping
    public ApiResponse<Item> saveItem(@RequestBody ItemDto item) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Item saved successfully.", itemService.save(item));
    }

    @PutMapping("/{id}")
    public ApiResponse<UserDto> update(@RequestBody ItemDto itemDto, @PathVariable String id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.", itemService.update(itemDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        itemService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", null);
    }
}
