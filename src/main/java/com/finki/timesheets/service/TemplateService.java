package com.finki.timesheets.service;

import com.finki.timesheets.model.Project;
import javassist.NotFoundException;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;


public interface TemplateService {

    ByteArrayInputStream coverLetterTemplate(String filename, Project project);

    ByteArrayInputStream invoiceTemplate(String filename, Project project);

    ByteArrayInputStream solutionContractTemplate(String filename, Project project) throws NotFoundException;

    ByteArrayInputStream requirementContractTemplate(String filename, Project project) throws NotFoundException;

    ByteArrayInputStream downloadAllTemplates(ArrayList<String> filenames, Project project);

}
