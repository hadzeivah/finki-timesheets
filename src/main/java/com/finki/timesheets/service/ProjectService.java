package com.finki.timesheets.service;

import com.finki.timesheets.model.Project;
import javassist.NotFoundException;

import java.util.List;

public interface ProjectService {
    List<Project> findAll();

    Project findById(Long id);

    void delete(Long id);

    Project update(Project project);

    Project save(Project project) throws NotFoundException;
}
