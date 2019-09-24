package com.finki.timesheets.service;

import com.finki.timesheets.model.Project;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;


public interface TemplateService {

    ResponseEntity coverLetterTemplate(String filename , Project project);

    ResponseEntity invoiceTemplate(String filename, Project project);

    ResponseEntity solutionContractTemplate(String filename, Project project);

    ResponseEntity requirementContractTemplate(String filename, Project project);

    ResponseEntity getResourceFromFileDirectory(String filename);

    ResponseEntity downloadAllTemplates(ArrayList<String> filenames, Project project);

}
