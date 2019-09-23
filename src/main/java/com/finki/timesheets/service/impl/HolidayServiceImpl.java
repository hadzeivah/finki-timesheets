package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Holiday;
import com.finki.timesheets.repository.HolidayRepository;
import com.finki.timesheets.service.HolidayService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("holidayService")
public class HolidayServiceImpl  implements HolidayService {

    private HolidayRepository holidayRepository;

    public HolidayServiceImpl(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    @Override
    public List<Holiday> findAllHolidays() {
        return this.holidayRepository.findAll();
    }
}
