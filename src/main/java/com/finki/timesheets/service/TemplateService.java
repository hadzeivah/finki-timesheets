package com.finki.timesheets.service;

import java.io.FileNotFoundException;

public interface TemplateService {

    void coverLetterTemplate() throws Exception;

    void invoiceTemplate() throws Exception;

    void solutionContractTemplate();

    void requirementContractTemplate();
}
