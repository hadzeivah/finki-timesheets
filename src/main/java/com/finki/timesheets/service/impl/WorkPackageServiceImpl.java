package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.WorkPackage;
import com.finki.timesheets.repository.WorkPackageRepository;
import com.finki.timesheets.service.WorkPackageService;
import javassist.NotFoundException;
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

    @Override
    public WorkPackage findOne(Long id) throws NotFoundException {
        return this.workPackageRepository.findById(id).orElseThrow(() -> new NotFoundException("Invalid Id:" + id));
    }

    @Override
    public void delete(Long id) {
        this.workPackageRepository.deleteById(id);
    }
}
