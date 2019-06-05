package com.finki.timesheets.service;

import org.springframework.http.ResponseEntity;


public interface TemplateService {

    ResponseEntity coverLetterTemplate(String filename);

    ResponseEntity invoiceTemplate(String filename);

    ResponseEntity solutionContractTemplate(String filename);

    ResponseEntity requirementContractTemplate(String filename);

    ResponseEntity getFileSystem(String filename);
}
