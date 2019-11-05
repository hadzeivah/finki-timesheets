package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.WorkPackage;
import com.finki.timesheets.repository.WorkPackageRepository;
import com.finki.timesheets.service.WorkPackageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "workPackageService")
public class WorkPackageServiceImpl implements WorkPackageService {

    private final WorkPackageRepository workPackageRepository;

    public WorkPackageServiceImpl(WorkPackageRepository workPackageRepository) {
        this.workPackageRepository = workPackageRepository;
    }

    @Override
    public List<WorkPackage> findAll() {
        return this.workPackageRepository.findAll();
    }
}
