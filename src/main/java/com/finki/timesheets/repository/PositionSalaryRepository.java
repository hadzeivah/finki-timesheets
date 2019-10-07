package com.finki.timesheets.repository;

import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.ProjectPosition;
import com.finki.timesheets.model.dto.ProjectPositionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PositionSalaryRepository extends JpaRepository<ProjectPosition, ProjectPositionKey> {
    List<ProjectPosition> findAllByProjectId(Long id);

    ProjectPosition findByProjectAndAndPosition(Project project, Position position);
}