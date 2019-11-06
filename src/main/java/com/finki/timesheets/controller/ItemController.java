package com.finki.timesheets.controller;


import com.finki.timesheets.model.ApiResponse;
import com.finki.timesheets.model.Item;
import com.finki.timesheets.model.dto.ItemDto;
import com.finki.timesheets.service.ItemService;
import com.finki.timesheets.service.utils.CsvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<String> importTimesheetItems(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException {

        this.itemService.saveAll(CsvUtils.read(Item.class, file.getInputStream()));
        return new ResponseEntity<String>("ime", HttpStatus.OK);
    }
}
