package com.finki.timesheets.repository;

import com.finki.timesheets.model.WorkingHoursSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkingHoursSummaryRepository extends JpaRepository<WorkingHoursSummary, Long> {
    WorkingHoursSummary findWorkingHoursSummaryByMemberIdAndDate(Long memberId, LocalDate localDate);

    List<WorkingHoursSummary> findWorkingHoursSummaryByMemberId(Long memberId);
}
