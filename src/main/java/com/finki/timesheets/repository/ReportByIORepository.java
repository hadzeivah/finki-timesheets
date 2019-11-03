package com.finki.timesheets.repository;

import com.finki.timesheets.model.ReportByIO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportByIORepository extends JpaRepository<ReportByIO, Long> {
}
