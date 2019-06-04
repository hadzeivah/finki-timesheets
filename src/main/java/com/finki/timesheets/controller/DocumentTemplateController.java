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

  /*  @GetMapping("/invoice")
    public void getInvoiceTemplate() throws Exception {
        templateService.invoiceTemplate();
    }

    @GetMapping("/solution")
    public void getSolutionContractTemplate() throws Exception {
        templateService.solutionContractTemplate();
    }

    @GetMapping("/requirement")
    public void getRequirementContract() throws Exception {
        templateService.requirementContractTemplate();
    }

    @GetMapping("/coverLetter")
    public void getCoverLetterTemplate() throws Exception {
        templateService.coverLetterTemplate();
    }*/
    @GetMapping(value = "/{filename}", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.documentrtg; charset=utf-8")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getFileFromFileSystem(@PathVariable String filename, HttpServletResponse response) {
        return templateService.getFileSystem(filename, response);
    }
}
