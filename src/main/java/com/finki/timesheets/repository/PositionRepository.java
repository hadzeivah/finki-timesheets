package com.finki.timesheets.repository;

import com.finki.timesheets.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PositionRepository extends JpaRepository<Position,Long> {
}