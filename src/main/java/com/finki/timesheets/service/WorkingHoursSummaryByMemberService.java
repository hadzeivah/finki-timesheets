package com.finki.timesheets.service;

import com.finki.timesheets.model.WorkingHoursSummary;

import java.time.LocalDate;
import java.util.List;

public interface WorkingHoursSummaryByMemberService {

    WorkingHoursSummary findByMemberIdAndDate(Long memberId, LocalDate localDate);

    List<WorkingHoursSummary> findByMemberId(Long memberId);
}
