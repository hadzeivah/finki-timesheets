package com.finki.timesheets.repository;

import com.finki.timesheets.model.WorkPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkPackageRepository extends JpaRepository<WorkPackage, Long> {
}
