package com.finki.timesheets.controller;


import com.finki.timesheets.model.Project;
import com.finki.timesheets.service.ProjectService;
import com.finki.timesheets.service.TemplateService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/templates")
public class DocumentTemplateController {

    private TemplateService templateService;
    private ProjectService projectService;

    @Autowired
    public DocumentTemplateController(TemplateService templateService, ProjectService projectService) {
        this.templateService = templateService;
        this.projectService = projectService;
    }

    @GetMapping(value = "project/{projectId}", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.documentrtg; charset=utf-8")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getFileFromFileSystem(@RequestParam String filenames, @PathVariable Long projectId) throws NotFoundException {

        ArrayList<String> filenamesList = new ArrayList<>(Arrays.asList(filenames.split(",")));
        Project project = this.projectService.findById(projectId);

        if (filenamesList.size() > 1) {
            return templateService.downloadAllTemplates(filenamesList, project);
        } else {
            String filename = filenamesList.get(0);
            switch (filename) {
                case "invoice":
                    return templateService.invoiceTemplate(filename, project);
                case "solution":
                    return templateService.solutionContractTemplate(filename, project.getId());
                case "requirement":
                    return templateService.requirementContractTemplate(filename, project.getId());
                case "coverLetter":
                    return templateService.coverLetterTemplate(filename, project);
                default:
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }
    }
}