package com.finki.timesheets.controller;


import com.finki.timesheets.model.Project;
import com.finki.timesheets.service.MemberService;
import com.finki.timesheets.service.ProjectService;
import com.finki.timesheets.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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

    @GetMapping(value = "project/{projectId}/{filename}", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.documentrtg; charset=utf-8")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getFileFromFileSystem(@PathVariable String filename, @PathVariable Long projectId) {

        Project project = this.projectService.findById(projectId);

        if (project != null) {

            switch (filename) {
                case "invoice":
                    return templateService.invoiceTemplate(filename, project);
                case "solution":
                    return templateService.solutionContractTemplate(filename, projectId);
                case "requirement":
                    return templateService.requirementContractTemplate(filename, projectId);
                case "coverLetter":
                    return templateService.coverLetterTemplate(filename,project);
                default:
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}