package com.finki.timesheets.service;

import com.finki.timesheets.model.Project;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;


public interface TemplateService {

    ByteArrayInputStream coverLetterTemplate(String filename, Project project);

    ByteArrayInputStream invoiceTemplate(String filename, Project project);

    ByteArrayInputStream solutionContractTemplate(String filename, Project project);

    ByteArrayInputStream requirementContractTemplate(String filename, Project project);

    ByteArrayInputStream downloadAllTemplates(ArrayList<String> filenames, Project project);

}
