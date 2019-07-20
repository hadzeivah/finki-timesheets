package com.finki.timesheets.service;

import com.finki.timesheets.model.Project;
import org.springframework.http.ResponseEntity;


public interface TemplateService {

    ResponseEntity coverLetterTemplate(String filename , Project project);

    ResponseEntity invoiceTemplate(String filename, Project project);

    ResponseEntity solutionContractTemplate(String filename, Long projectId);

    ResponseEntity requirementContractTemplate(String filename, Long projectId);

    ResponseEntity getFileSystem(String filename);
}
