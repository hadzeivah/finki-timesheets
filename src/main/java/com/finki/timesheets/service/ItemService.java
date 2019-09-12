package com.finki.timesheets.service;

import com.finki.timesheets.model.Item;
import com.finki.timesheets.model.dto.ItemDto;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    Item save(ItemDto item);

    List<Item> findItemsByTimesheet(Long timesheetId);

    void delete(Long id);

    Item findById(Long id);

    ItemDto update(ItemDto itemDto);
}
