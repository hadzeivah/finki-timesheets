package com.finki.timesheets.controller;


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

    @Autowired
    public DocumentTemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping(value = "/{filename}", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.documentrtg; charset=utf-8")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getFileFromFileSystem(@PathVariable String filename, HttpServletResponse response) {
        switch (filename) {
            case "invoice":
                return templateService.invoiceTemplate(filename);
            case "solution":
                return templateService.solutionContractTemplate(filename);
            case "requirement":
                return templateService.requirementContractTemplate(filename);
            case "coverLetter":
                return templateService.coverLetterTemplate(filename);
            default:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }
}