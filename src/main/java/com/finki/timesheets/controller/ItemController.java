package com.finki.timesheets.controller;


import com.finki.timesheets.model.*;
import com.finki.timesheets.model.dto.ItemDto;
import com.finki.timesheets.service.ItemService;
import com.finki.timesheets.service.OutputService;
import com.finki.timesheets.service.TaskService;
import com.finki.timesheets.service.TimesheetService;
import javassist.NotFoundException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;
    private final TimesheetService timesheetService;
    private OutputService outputService;
    private TaskService taskService;
    private final int DATE_CELL_INDEX = 0;
    private final int HOURS_CELL_INDEX = 1;
    private final int TASKS_CELL_INDEX = 2;
    private final int OUTPUT_CELL_INDEX = 3;


    @Autowired
    public ItemController(ItemService itemService, TimesheetService timesheetService, OutputService outputService, TaskService taskService) {
        this.itemService = itemService;
        this.timesheetService = timesheetService;
        this.outputService = outputService;
        this.taskService = taskService;
    }

    @GetMapping(params = {"timesheetId"})
    public List<Item> findItemsByTimesheetId(@RequestParam("timesheetId") Long timesheetId) {
        try {
            return itemService.findItemsByTimesheet(timesheetId);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping
    public Item saveItem(@RequestBody ItemDto item) throws Exception {
        return itemService.save(item);
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

        XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = wb.getSheetAt(0);

        List<Item> items = new ArrayList<>();
        Timesheet timesheet = this.timesheetService.findById(id);
        sheet.removeRow(sheet.getRow(0));

        for (Row row : sheet) {
            Output output = this.outputService.findByDescription(row.getCell(OUTPUT_CELL_INDEX).getStringCellValue());
            Task task = this.taskService.findByDescription(row.getCell(TASKS_CELL_INDEX).getStringCellValue());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate localDate = LocalDate.parse(row.getCell(DATE_CELL_INDEX).getStringCellValue(), formatter);
            Item item = new Item(timesheet, localDate, (int) row.getCell(HOURS_CELL_INDEX).getNumericCellValue(),
                    task, output);
            items.add(item);
        }

        this.itemService.importItems(items);
        return new ResponseEntity<>(file.getName(), HttpStatus.OK);
    }
}
