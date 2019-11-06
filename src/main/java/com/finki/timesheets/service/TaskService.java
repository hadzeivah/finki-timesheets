package com.finki.timesheets.service;

import com.finki.timesheets.model.Task;

import java.util.List;

public interface TaskService {
    List<Task> saveAll(List<Task> tasks);
}
