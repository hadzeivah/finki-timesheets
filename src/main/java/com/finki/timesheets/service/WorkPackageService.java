package com.finki.timesheets.service;

import com.finki.timesheets.model.WorkPackage;
import javassist.NotFoundException;

import java.util.List;

public interface WorkPackageService {
    List<WorkPackage> findAll();

    WorkPackage findOne(Long id) throws NotFoundException;

    void delete(Long id);

    WorkPackage save(WorkPackage workPackage);
}
