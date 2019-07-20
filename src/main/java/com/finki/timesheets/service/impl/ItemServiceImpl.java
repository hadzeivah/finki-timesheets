package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Item;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.repository.ItemRepository;
import com.finki.timesheets.repository.TimesheetRepository;
import com.finki.timesheets.service.ItemService;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
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
    public Item save(Item newItem) throws NotFoundException {
        Item item = new Item();
        item.setTimesheet(timesheetRepository.findById(newItem.getTimesheet().getId()).orElseThrow(() -> new NotFoundException("Timesheet not found")));
        item.setStartDate(newItem.getStartDate());
        item.setEndDate(newItem.getEndDate());
        item.setHours(newItem.getHours());
        item.setTaskDescription(newItem.getTaskDescription());
        item.setIntellectualOutput(newItem.getIntellectualOutput());
        return itemRepository.save(item);
    }

    @Override
    public Optional<List<Item>> findItemsByTimesheet(Long id) {
        Optional<Timesheet> timesheet = timesheetRepository.findById(id);
        return itemRepository.findItemsByTimesheet(timesheet.orElseThrow(() -> new IllegalArgumentException("Invalid timesheet Id:" + id)));
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
    public Item update(Item prevItem) {
        Item item = findById(prevItem.getId());
        if (item != null) {
            BeanUtils.copyProperties(prevItem, item);
            itemRepository.save(item);
        }
        return item;
    }
}
