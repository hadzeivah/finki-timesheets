package com.finki.timesheets.service;

import com.finki.timesheets.model.Task;

import java.util.List;

public interface TaskService {
    List<Task> saveAll(List<Task> tasks);

    void delete(Long id);

    Task findByDescription(String stringCellValue);

    Task save(Task task);
}
