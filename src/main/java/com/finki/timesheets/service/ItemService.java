package com.finki.timesheets.service;

import com.finki.timesheets.model.Item;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.model.dto.ItemDto;
import javassist.NotFoundException;

import java.util.List;

public interface ItemService {
    Item save(ItemDto item) throws Exception;

    List<Item> findItemsByTimesheet(Long timesheetId) throws NotFoundException;

    Long calculateTotalHoursSpentByTimesheet(Timesheet timesheet) throws NotFoundException;

    void delete(Long id);

    Item findById(Long id);

    ItemDto update(ItemDto itemDto);

    List<Item> saveAll(List<Item> items);

    void importItems(List<Item> items, Timesheet timesheet);
}