package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.University;
import com.finki.timesheets.repository.UniversityRepository;
import com.finki.timesheets.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "universityService")

public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;

    @Autowired
    public UniversityServiceImpl(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @Override
    public List<University> findAll() {
        return universityRepository.findAll();
    }

    @Override
    public Optional<University> findById(Long id) {
        return universityRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
      universityRepository.deleteById(id);
    }
    @Override
    public University update(University university) {
        return null;
    }

    @Override
    public University save(University university) {
        return null;
    }
}
