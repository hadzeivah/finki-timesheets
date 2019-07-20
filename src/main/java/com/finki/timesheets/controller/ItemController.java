package com.finki.timesheets.controller;


import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Item;
import com.finki.timesheets.service.ItemService;
import javassist.NotFoundException;
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

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(params = {"timesheetId"})
    public Optional<List<Item>> findItemsByTimesheetId(@RequestParam("timesheetId") Long timesheetId) {
        return itemService.findItemsByTimesheet(timesheetId);
    }

    @PostMapping
    public ApiResponse<Item> saveItem(@RequestBody Item item) throws NotFoundException {
        return new ApiResponse<>(HttpStatus.OK.value(), "Item saved successfully.", itemService.save(item));
    }

    @PutMapping("/{id}")
    public ApiResponse<Item> update(@RequestBody Item item) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Item updated successfully.", itemService.update(item));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        itemService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Item fetched successfully.", null);
    }
}
