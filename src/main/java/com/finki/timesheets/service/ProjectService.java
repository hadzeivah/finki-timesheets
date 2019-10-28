package com.finki.timesheets.service;

import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.User;
import javassist.NotFoundException;

import java.util.List;

public interface ProjectService {
    List<Project> findAllByProjectManagerIsDeletedFalseAndIsApprovedTrue(User user);

    List<Project> findAllUnapprovedProjects();

    Project findById(Long id) throws NotFoundException;

    void delete(Long id);

    Project update(Project project) throws NotFoundException;

    Project save(Project project) throws NotFoundException;
}
