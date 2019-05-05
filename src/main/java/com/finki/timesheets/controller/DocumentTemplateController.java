package com.finki.timesheets.controller;


import com.finki.timesheets.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/templates")
public class DocumentTemplateController {

    private TemplateService templateService;

    @Autowired
    public DocumentTemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping()
    public void findItemsByTimesheetId() {
        templateService.solutionContractTemplate();
    }


}
