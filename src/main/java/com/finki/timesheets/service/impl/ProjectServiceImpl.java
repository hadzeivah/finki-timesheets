package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Project;
import com.finki.timesheets.repository.ProjectRepository;
import com.finki.timesheets.service.ProjectService;
import com.finki.timesheets.service.UniversityService;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "projectService")
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private UniversityService universityService;

    public ProjectServiceImpl(ProjectRepository projectRepository, UniversityService universityService) {
        this.projectRepository = projectRepository;
        this.universityService = universityService;
    }


    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project findById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + id));
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public Project update(Project project) throws NotFoundException {
        project.setUniversity(universityService.findById(1L).orElseThrow(() -> new NotFoundException("University not found")));
        return projectRepository.save(project);
    }

    @Override
    public Project save(Project project) throws NotFoundException {
        project.setUniversity(universityService.findById(1L).orElseThrow(() -> new NotFoundException("University not found")));
        return projectRepository.save(project);
    }
}
