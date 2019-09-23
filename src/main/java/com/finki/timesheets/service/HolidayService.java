package com.finki.timesheets.service;

import com.finki.timesheets.model.Holiday;

import java.util.List;

public interface HolidayService {
    List<Holiday> findAllHolidays();
}
