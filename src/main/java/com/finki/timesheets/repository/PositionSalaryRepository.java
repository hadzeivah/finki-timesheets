package com.finki.timesheets.repository;

import com.finki.timesheets.model.PositionSalary;
import com.finki.timesheets.model.dto.PositionSalaryKey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PositionSalaryRepository extends JpaRepository<PositionSalary, PositionSalaryKey> {
    List<PositionSalary> findAllByProjectId(Long id);
}