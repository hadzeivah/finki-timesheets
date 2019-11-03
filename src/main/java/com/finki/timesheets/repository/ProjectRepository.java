package com.finki.timesheets.repository;

import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByProjectManagerAndIsDeletedFalse(User user);

    List<Project> findAllByIsApprovedFalse();
}