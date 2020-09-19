package com.finki.timesheets.repository;

import com.finki.timesheets.model.Item;
import com.finki.timesheets.model.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    Optional<List<Item>> findItemsByTimesheet(Timesheet timesheet);

    int countByIntellectualOutputId(Long id);

    int countByTaskDescriptionId(Long id);
}