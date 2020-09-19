package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Item;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.model.WorkingHoursSummary;
import com.finki.timesheets.model.dto.ItemDto;
import com.finki.timesheets.repository.ItemRepository;
import com.finki.timesheets.service.ItemService;
import com.finki.timesheets.service.TimesheetService;
import com.finki.timesheets.service.WorkingHoursSummaryByMemberService;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "itemService")
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private static final int MAX_HOURS = 8;
    private final TimesheetService timesheetService;
    private WorkingHoursSummaryByMemberService workingHoursSummaryByMemberService;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, TimesheetService timesheetService, WorkingHoursSummaryByMemberService workingHoursSummaryByMemberService) {
        this.itemRepository = itemRepository;
        this.timesheetService = timesheetService;
        this.workingHoursSummaryByMemberService = workingHoursSummaryByMemberService;
    }

    @Override
    public Item save(ItemDto item) throws Exception {
        Timesheet timesheet;
        Item newItem;

        timesheet = timesheetService.findById(item.getTimesheetId());
        newItem = new Item(timesheet, item.getDate(), item.getHours(), item.getTaskDescription(), item.getIntellectualOutput());

        WorkingHoursSummary workingHoursSummaryByMember = this.workingHoursSummaryByMemberService.findByMemberIdAndDate(timesheet.getMember().getId(), item.getDate());

        if (workingHoursSummaryByMember != null && workingHoursSummaryByMember.getHours() > MAX_HOURS) {
            throw new Exception("The maximum hours per day has been exceeded");
        } else
            return itemRepository.save(newItem);
    }

    @Override
    public List<Item> findItemsByTimesheet(Long id) throws NotFoundException {
        Optional<Timesheet> timesheet = Optional.ofNullable(timesheetService.findById(id));
        return itemRepository.findItemsByTimesheet(timesheet.orElseThrow(() -> new IllegalArgumentException("Invalid timesheet Id:" + id))).orElse(new ArrayList<>());
    }

    @Override
    public Long calculateTotalHoursSpentByTimesheet(Timesheet timesheet) throws NotFoundException {

        List<Item> items = findItemsByTimesheet(timesheet.getId());

        if (items.isEmpty())
            return 0L;
        else
            return items.stream().mapToLong(Item::getHours).sum();
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

    @Override
    public List<Item> saveAll(List<Item> items) {
        return itemRepository.saveAll(items);
    }

    @Transactional
    @Override
    public void importItems(@Valid List<Item> items) {
        this.saveAll(items);
    }
}
