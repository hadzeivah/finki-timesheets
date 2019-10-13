package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Constants;
import com.finki.timesheets.model.Item;
import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.service.ReportService;
import com.finki.timesheets.service.TemplateService;
import com.finki.timesheets.service.TimesheetService;
import com.finki.timesheets.service.utils.StringUtils;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service(value = "templateService")
public class TemplateServiceImpl implements TemplateService {


    private static final String CLASS_PATH = "/templates/";
    private static final String FILE_DIRECTORY = "D:\\Ivan_Chorbev-Templates\\";
    private TimesheetService timesheetService;
    private ReportService reportService;
    private HashMap<String, String> replacementValues = new HashMap<>();
    private String today = StringUtils.formatDateToString_DDMMYYYY(LocalDateTime.now());


    public TemplateServiceImpl(TimesheetService timesheetService, ReportService reportService) {
        this.timesheetService = timesheetService;
        this.reportService = reportService;
    }

    @Override
    public ByteArrayInputStream downloadAllTemplates(ArrayList<String> filenames, Project project) {

        ArrayList<String> filenamesAsDocx = new ArrayList<>();
        Map<String, ByteArrayInputStream> documents = new HashMap<>();

        filenames.forEach(filename -> {
            switch (filename) {
                case "invoice":
                    documents.put(filename + ".docx", invoiceTemplate(filename, project));
                    break;
                case "solution":
                    documents.put(filename + ".docx", solutionContractTemplate(filename, project));
                    break;
                case "requirement":
                    documents.put(filename + ".docx", requirementContractTemplate(filename, project));
                    break;
                case "coverLetter":
                    documents.put(filename + ".docx", coverLetterTemplate(filename, project));
            }
            filenamesAsDocx.add(filename + ".docx");
        });


        return ZipMultipleFiles(filenamesAsDocx, documents);
    }


    @Override
    public ByteArrayInputStream coverLetterTemplate(String filename, Project project) {
        String filepath = CLASS_PATH + "Propratno06 - Copy.docx";
        XWPFDocument doc = openDocument(filepath);
        this.buildPlaceholders(project);
        replaceText(doc, replacementValues);
        replaceCell(doc, replacementValues);

        return saveDocument(doc);
    }

    @Override
    public ByteArrayInputStream invoiceTemplate(String filename, Project project) {
        String filepath = CLASS_PATH + "Faktura06 - Copy.docx";

        XWPFDocument doc = openDocument(filepath);

        this.buildPlaceholders(project);

        replaceCell(doc, replacementValues);
        replaceText(doc, replacementValues);

        return saveDocument(doc);
    }

    @Override
    public ByteArrayInputStream requirementContractTemplate(String filename, Project project) {
        String filepath = CLASS_PATH + "BaranjeTS6 - Copy.docx";

        this.buildPlaceholders(project);
        return generateTimesheetTableByProject(project.getId(), filepath);
    }

    @Override
    public ByteArrayInputStream solutionContractTemplate(String filename, Project project) {
        String filepath = CLASS_PATH + "Resenie06 - Copy.docx";
        this.buildPlaceholders(project);
        return generateTimesheetTableByProject(project.getId(), filepath);
    }

    private ByteArrayInputStream generateTimesheetTableByProject(Long projectId, String filepath) {
        XWPFDocument doc = openDocument(filepath);
        List<Timesheet> timesheets = timesheetService.findTimesheetsByProject(projectId);
        replaceTable(doc, timesheets);
        replaceCell(doc, replacementValues);
        replaceText(doc, replacementValues);
        return saveDocument(doc);
    }

    private XWPFDocument replaceTable(XWPFDocument doc, List<Timesheet> timesheets) {
        XWPFTable table = null;
        int count = 0;
        for (XWPFParagraph paragraph : doc.getParagraphs()) {
            List<XWPFRun> runs = paragraph.getRuns();
            String find = "%TABLE";
            TextSegment found = paragraph.searchText(find, new PositionInParagraph());
            if (found != null) {
                count++;
                if (found.getBeginRun() == found.getEndRun()) {
                    // whole search string is in one Run
                    XmlCursor cursor = paragraph.getCTP().newCursor();
                    table = doc.insertNewTbl(cursor);
                    XWPFRun run = runs.get(found.getBeginRun());
                    // Clear the "%TABLE" from doc
                    String runText = run.getText(run.getTextPosition());
                    String replaced = runText.replace(find, "");
                    run.setText(replaced, 0);
                } else {
                    // The search string spans over more than one Run
                    StringBuilder b = new StringBuilder();
                    for (int runPos = found.getBeginRun(); runPos <= found.getEndRun(); runPos++) {
                        XWPFRun run = runs.get(runPos);
                        b.append(run.getText(run.getTextPosition()));
                    }
                    String connectedRuns = b.toString();
                    XmlCursor cursor = paragraph.getCTP().newCursor();
                    table = doc.insertNewTbl(cursor);
                    String replaced = connectedRuns.replace(find, ""); // Clear search text

                    // The first Run receives the replaced String of all connected Runs
                    XWPFRun partOne = runs.get(found.getBeginRun());
                    partOne.setText(replaced, 0);
                    // Removing the text in the other Runs.
                    for (int runPos = found.getBeginRun() + 1; runPos <= found.getEndRun(); runPos++) {
                        XWPFRun partNext = runs.get(runPos);
                        partNext.setText("", 0);
                    }
                }
            }

        }

        if (table != null) {
            fillTimesheetTable(doc, table, timesheets);
        }
        return doc;
    }

    private XWPFDocument fillTimesheetTable(XWPFDocument doc, XWPFTable table, List<Timesheet> timesheets) {
        int currRow = 0;
        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText("Бр.");
        header.addNewTableCell().setText("Име и презиме");
        header.addNewTableCell().setText("Матичен број \n Трансакциска сметка");
        header.addNewTableCell().setText("Вид на активност");
        header.addNewTableCell().setText("Бр. час.");
        header.addNewTableCell().setText("€ \n" + "час\n");
        header.addNewTableCell().setText("€");
        header.addNewTableCell().setText("MKD");

        for (Timesheet t : timesheets) {
            XWPFTableRow curRow = table.createRow();
            currRow++;
            Optional<Item> item = t.getItems().stream().findFirst();
            Long totalHoursSpent = timesheetService.calculateTotalHoursSpentByTimesheet(t);
            double totalEuros = totalHoursSpent / 24.0 * t.getPositionSalary().getSalary();
            totalEuros = Math.round(totalEuros * 10) / 10.0;
            double totalMKD = totalEuros * Constants.EUR;
            totalMKD = Math.round(totalMKD * 10) / 10.0;

            curRow.getCell(0).setText(String.valueOf(currRow));
            curRow.getCell(1).setText(t.getMember().getFullName());
            curRow.getCell(2).setText(t.getMember().getEmbg() + " " + t.getMember().getTransactionAccount());
            curRow.getCell(3).setText(item.isPresent() ? item.get().getTaskDescription() : "");
            curRow.getCell(4).setText(totalHoursSpent.toString());
            curRow.getCell(5).setText(String.valueOf(t.getPositionSalary().getSalary()));
            curRow.getCell(6).setText(String.valueOf(totalEuros));
            curRow.getCell(7).setText(String.valueOf(totalMKD));
        }
        return doc;
    }

    private void replaceText(XWPFDocument doc, HashMap<String, String> replacementValues) {
        for (XWPFParagraph p : doc.getParagraphs()) {
            replaceParagraph(p, replacementValues);
        }
    }

    private void replaceCell(XWPFDocument doc, HashMap<String, String> replacementValues) {
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        replaceParagraph(p, replacementValues);
                    }
                }
            }
        }
    }

    private void replaceParagraph(XWPFParagraph paragraph, Map<String, String> fieldsForReport) throws POIXMLException {
        String find, text, runsText;
        List<XWPFRun> runs;
        XWPFRun run, nextRun;
        for (String key : fieldsForReport.keySet()) {
            text = paragraph.getText();
            if (!text.contains("${"))
                return;
            find = "${" + key + "}";
            if (!text.contains(find))
                continue;
            runs = paragraph.getRuns();
            for (int i = 0; i < runs.size(); i++) {
                run = runs.get(i);
                runsText = run.getText(0);
                if (runsText != null && (runsText.contains("${") || (runsText.contains("$") && runs.get(i + 1).getText(0).substring(0, 1).equals("{")))) {
                    while (!runsText.contains("}")) {
                        nextRun = runs.get(i + 1);
                        runsText = runsText + nextRun.getText(0);
                        paragraph.removeRun(i + 1);
                    }
                    run.setText(runsText.contains(find) ?
                            runsText.replace(find, fieldsForReport.get(key)) :
                            runsText, 0);
                }
            }
        }
    }

    private XWPFDocument openDocument(String file) {
        Resource resource = new ClassPathResource(file);
        XWPFDocument document = null;
        try {
            InputStream fis = new FileInputStream(resource.getFile());
            document = new XWPFDocument(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return document;
    }

    private ByteArrayInputStream saveDocument(XWPFDocument doc) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            doc.write(out);
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    private void buildPlaceholders(Project project) {

        String from = StringUtils.formatDateToString_DDMMYYYY(project.getStartDate() != null ? project.getStartDate() : LocalDateTime.now());
        String to = StringUtils.formatDateToString_DDMMYYYY(project.getEndDate() != null ? project.getEndDate() : LocalDateTime.now());
        double total = this.reportService.calculateTotalSalaryForProject(project) * Constants.EUR;
        replacementValues.put("from", from);
        replacementValues.put("to", to);
        replacementValues.put("dean", project.getUniversity().getDean());
        replacementValues.put("university", project.getUniversity().getName());
        replacementValues.put("project", project.getName());
        replacementValues.put("number", project.getProjectNumber());
        replacementValues.put("today", today);
        replacementValues.put("total", String.valueOf(total));
    }

    private ByteArrayInputStream ZipMultipleFiles(ArrayList<String> srcFiles, Map<String, ByteArrayInputStream> documents) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(bos);

        try {
            for (String srcFile : srcFiles) {
                ZipEntry zipEntry = new ZipEntry(srcFile);
                zipOut.putNextEntry(zipEntry);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = documents.get(srcFile).read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
            }
            zipOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(bos.toByteArray());
    }
}