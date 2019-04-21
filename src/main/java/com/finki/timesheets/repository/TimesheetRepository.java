package com.finki.timesheets.repository;

import com.finki.timesheets.model.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {
    Optional<Timesheet> findTimesheetByProjectIdAndMemberId(Long projectId , Long memberId);
}