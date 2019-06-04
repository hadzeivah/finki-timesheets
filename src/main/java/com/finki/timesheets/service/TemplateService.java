package com.finki.timesheets.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface TemplateService {

    void coverLetterTemplate() throws Exception;

    void invoiceTemplate() throws Exception;

    void solutionContractTemplate();

    void requirementContractTemplate();

    ResponseEntity getFileSystem(String filename, HttpServletResponse response);
}
