package com.finki.timesheets.repository;

import com.finki.timesheets.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByDescription(String description);
}
