package com.finki.timesheets.repository;

import com.finki.timesheets.model.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {
}