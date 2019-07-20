package com.finki.timesheets.service;

import com.finki.timesheets.model.University;

import java.util.List;
import java.util.Optional;

public interface UniversityService {
    List<University> findAll();

    Optional<University> findById(Long id);

    void delete(Long id);

    University update(University university);

    University save(University university);
}
