package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Item;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.model.dto.ItemDto;
import com.finki.timesheets.repository.ItemRepository;
import com.finki.timesheets.repository.TimesheetRepository;
import com.finki.timesheets.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<Item> findItemsByTimesheet(Long id) {
        Optional<Timesheet> timesheet = timesheetRepository.findById(id);
        return itemRepository.findItemsByTimesheet(timesheet.orElseThrow(() -> new IllegalArgumentException("Invalid timesheet Id:" + id))).orElse(new ArrayList<>());
    }

    @Override
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public Item findById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        return optionalItem.orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));
    }

    @Override
    public ItemDto update(ItemDto itemDto) {
        Item item = findById(itemDto.getId());
        if (item != null) {
            BeanUtils.copyProperties(itemDto, item);
            itemRepository.save(item);
        }
        return itemDto;
    }
}
