package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.User;
import com.finki.timesheets.repository.ProjectRepository;
import com.finki.timesheets.service.ProjectService;
import com.finki.timesheets.service.WorkPackageService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "projectService")
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private WorkPackageService workPackageService;

    public ProjectServiceImpl(ProjectRepository projectRepository, WorkPackageService workPackageService) {
        this.projectRepository = projectRepository;
        this.workPackageService = workPackageService;
    }


    @Override
    public List<Project> findAllByProjectManagerIsDeletedFalse(User user) {
        return projectRepository.findAllByProjectManagerAndIsDeletedFalse(user);
    }

    @Override
    public List<Project> findAllUnapprovedProjects() {
        return projectRepository.findAllByIsApprovedFalse();
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
    public Project update(Project project) throws NotFoundException {

        Project previous = findById(project.getId());
        project.setApproved(previous.getApproved());
        project.setDeleted(previous.getDeleted());

        return projectRepository.save(project);
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }
}
