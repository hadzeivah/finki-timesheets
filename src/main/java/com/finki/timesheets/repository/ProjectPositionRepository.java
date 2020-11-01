package com.finki.timesheets.repository;

import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.ProjectPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectPositionRepository extends JpaRepository<ProjectPosition, Long> {
    List<ProjectPosition> findAllByProjectId(Long id);

    ProjectPosition findByProjectAndPosition(Project project, Position position);

    void deleteByProjectAndPositionIn(Project project, List<Position> position);


}