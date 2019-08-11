package com.finki.timesheets.service;

import com.finki.timesheets.model.Project;
import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> findAll();

    Optional<Project> findById(Long id);

    void delete(Long id);

    Project update(Project project) throws NotFoundException;

    Project save(Project project) throws NotFoundException;
}
