package com.finki.timesheets.service;

import com.finki.timesheets.model.WorkingHoursSummary;

import java.time.LocalDate;

public interface WorkingHoursSummaryByMemberService {

    WorkingHoursSummary findByMemberIdAndDate(Long memberId, LocalDate localDate);

}
