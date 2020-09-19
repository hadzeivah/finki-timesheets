package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Task;
import com.finki.timesheets.repository.TaskRepository;
import com.finki.timesheets.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service(value = "taskService")
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> saveAll(List<Task> tasks) {
        return this.taskRepository.saveAll(tasks);
    }

    @Override
    public void delete(Long id) {
        this.taskRepository.deleteById(id);
    }

    @Override
    public Task findByDescription(String description) {
        return this.taskRepository.findByDescription(description);
    }
}
