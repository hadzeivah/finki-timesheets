package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Item;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.model.dto.ItemDto;
import com.finki.timesheets.repository.ItemRepository;
import com.finki.timesheets.repository.TimesheetRepository;
import com.finki.timesheets.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "itemService")
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final TimesheetRepository timesheetRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, TimesheetRepository timesheetRepository) {
        this.itemRepository = itemRepository;
        this.timesheetRepository = timesheetRepository;
    }

    @Override
    public Item save(ItemDto item) {
        Item newItem = new Item();
        newItem.setTimesheet(timesheetRepository.findById(item.getTimesheetId()).get());
        newItem.setStartDate(item.getStartDate());
        newItem.setEndDate(item.getEndDate());
        newItem.setHours(item.getHours());
        newItem.setTaskDescription(item.getTaskDescription());
        newItem.setIntellectualOutput(item.getIntellectualOutput());
        return itemRepository.save(newItem);
    }

    @Override
    public Optional<List<Item>> findItemsByTimesheet(Long timesheetId) {
        Optional<Timesheet> timesheet = timesheetRepository.findById(timesheetId);
        return itemRepository.findItemsByTimesheet(timesheet.orElse(new Timesheet()));
    }

    @Override
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public Item findById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        return optionalItem.orElse(null);
    }

    @Override
    public ItemDto update(ItemDto itemDto) {
        return null;
    }
}
