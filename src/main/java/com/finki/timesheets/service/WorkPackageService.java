package com.finki.timesheets.service;

import com.finki.timesheets.model.WorkPackage;

import java.util.List;

public interface WorkPackageService {
    List<WorkPackage> findAll();
}
