package com.finki.timesheets.controller;


import com.finki.timesheets.model.Project;
import com.finki.timesheets.service.ProjectService;
import com.finki.timesheets.service.TemplateService;
import com.finki.timesheets.service.utils.StringUtils;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/templates")
public class DocumentTemplateController {

    private final TemplateService templateService;
    private final ProjectService projectService;

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
        ByteArrayInputStream document = null;
        String documentName = "";
        String today = StringUtils.formatDateToString_DDMMYYYY(LocalDateTime.now());

        if (filenamesList.size() > 1) {
            documentName = String.format("%s_%s_%s.zip", "documents", project.getName(), today);
            document = templateService.downloadAllTemplates(filenamesList, project);
        } else {
            String filename = filenamesList.get(0);
            switch (filename) {
                case "invoice":
                    documentName = String.format("%s_%s_%s.docx", filename, project, today);
                    document = templateService.invoiceTemplate(filename, project);
                case "solution":
                    documentName = String.format("%s_%s_%s.docx", filename, project, today);
                    document = templateService.solutionContractTemplate(filename, project);
                case "requirement":
                    documentName = String.format("%s_%s_%s.docx", filename, project, today);
                    document = templateService.requirementContractTemplate(filename, project);
                case "coverLetter":
                    documentName = String.format("%s_%s_%s.docx", filename, project, today);
                    document = templateService.coverLetterTemplate(filename, project);
            }

        }

        if (document == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.documentrtg"))
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Access-Control-Expose-Headers", "Content-Disposition")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + documentName)
                .body(new InputStreamResource(document));

    }
}