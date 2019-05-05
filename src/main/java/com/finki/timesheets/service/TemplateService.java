package com.finki.timesheets.service;

import java.io.FileNotFoundException;

public interface TemplateService {

    void coverLetterTemplate() throws FileNotFoundException;

    void invoiceTemplate();

    void solutionContractTemplate();

    void requirementContractTemplate();
}
