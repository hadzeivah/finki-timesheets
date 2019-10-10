package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Project;
import com.finki.timesheets.repository.ProjectRepository;
import com.finki.timesheets.service.ProjectService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "projectService")
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @Override
    public List<Project> findAll() {
        return projectRepository.findAllByIsDeletedFalse();
    }

    @Override
    public Project findById(Long id) throws NotFoundException {
        return projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found"));
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public Project update(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }
}
