package com.finki.timesheets.service;

import com.finki.timesheets.model.Item;
import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    Item save(Item item) throws NotFoundException;

    Optional<List<Item>> findItemsByTimesheet(Long timesheetId);

    void delete(Long id);

    Item findById(Long id);

    Item update(Item item);
}
