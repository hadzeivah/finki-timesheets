package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.service.ProjectService;
import com.finki.timesheets.service.TemplateService;
import com.finki.timesheets.service.TimesheetService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service(value = "templateService")
public class TemplateServiceImpl implements TemplateService {


    private static final String CLASS_PATH = "/templates/";
    private TimesheetService timesheetService;
    private ProjectService projectService;

    public TemplateServiceImpl(TimesheetService timesheetService, ProjectService projectService) {
        this.timesheetService = timesheetService;
        this.projectService = projectService;
    }

    @Override
    public ResponseEntity getFileSystem(String filename, HttpServletResponse response) {
        return getResourceFromClassPath(filename, response);
    }

    private ResponseEntity getResourceFromClassPath(String filename, HttpServletResponse response) {
        Resource resource = new ClassPathResource(CLASS_PATH + filename);
        String downloadFileName = resource.getFilename();

        if(resource.exists()){
            try{
                InputStream fis = new FileInputStream(resource.getFile());
                InputStreamResource inputStreamResource = new InputStreamResource(fis);

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.documentrtg"))
                        .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS , "Access-Control-Expose-Headers", "Content-Disposition")
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadFileName)
                        .body(inputStreamResource);

            }
            catch (IOException e) {
                e.printStackTrace();
            }


        }

       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public void coverLetterTemplate() throws Exception {
        String filepath = CLASS_PATH + "Propratno06 - Copy.docx";
        String outpath = CLASS_PATH + "Test.docx";

        XWPFDocument doc = openDocument(filepath);

        if (doc != null) {
            replaceText(doc, "$$from$$", "10.05.2019", outpath);
            replaceText(doc, "$$to$$", "10.05.2019", outpath);
        }
    }

    @Override
    public void invoiceTemplate() {
        String filepath = CLASS_PATH + "Faktura06 - Copy.docx";
        String outpath = CLASS_PATH  + "FakturaTest.docx";

        XWPFDocument doc = openDocument(filepath);
        Project project = this.projectService.findById(1L);

        if (doc != null) {
            replaceCell(doc, "$$university$$", project.getUniversity().getName(), outpath);
            replaceCell(doc, "$$project$$", project.getName(), outpath);
            replaceText(doc, "$$dean$$", project.getUniversity().getDean(), outpath);
        }
    }

    @Override
    public void requirementContractTemplate() {
        String filepath = CLASS_PATH + "BaranjeTS6 - Copy.docx";
        String outpath = CLASS_PATH + "BaranjeTest.docx";

        generateTimesheetTableByProject(1L, filepath, outpath);

    }

    @Override
    public void solutionContractTemplate() {

        String filepath = CLASS_PATH + "Resenie06 - Copy.docx";
        String outpath = CLASS_PATH + "ResenieTest.docx";

        generateTimesheetTableByProject(1L, filepath, outpath);

    }

    private void generateTimesheetTableByProject(Long projectId, String filepath, String outpath) {
        XWPFDocument doc;
        try {
            doc = new XWPFDocument(OPCPackage.open(filepath));
            List<Timesheet> timesheets = timesheetService.findTimesheetsByProject(projectId);
            replaceTable(doc, timesheets, outpath);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();

        }
    }

    private void replaceTable(XWPFDocument doc, List<Timesheet> timesheets, String outpath) {
        XWPFTable table = null;
        long count = 0;
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
            fillTimesheetTable(doc, table, timesheets, outpath);
        }
    }

    private void fillTimesheetTable(XWPFDocument doc, XWPFTable table, List<Timesheet> timesheets, String outpath) {
        int currRow = 1;
        XWPFTableRow header = table.getRow(0);
        header.getCell(0).setText("Бр.");
        header.addNewTableCell().setText("Име и презиме");
        header.addNewTableCell().setText("Матичен број\n" + "Трансакциска сметка\n");
        header.addNewTableCell().setText("Вид на активност");
        header.addNewTableCell().setText("Бр. час.");
        header.addNewTableCell().setText("€ \n" + "час\n");


        for (Timesheet t : timesheets) {
            XWPFTableRow curRow = table.createRow();
            currRow++;
            curRow.getCell(0).setText(String.valueOf(currRow));
            curRow.getCell(1).setText(t.getMember().getFullName());
            curRow.getCell(2).setText(t.getMember().getEmbg() + " " + t.getMember().getTransactionAccount());
            curRow.getCell(3).setText("");
            curRow.getCell(4).setText(timesheetService.calculateTotalHoursSpentByTimesheet(t).toString());
            curRow.getCell(5).setText("");
        }
        saveDocument(doc, outpath);
    }

    private void replaceText(XWPFDocument doc, String find, String replace, String outpath) {
        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains(find)) {
                        text = text.replace(find, replace);
                        r.setText(text, 0);
                    }
                }
            }
            saveDocument(doc, outpath);
        }
    }

    private void replaceCell(XWPFDocument doc, String find, String replace, String outpath) {
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            String text = r.getText(0);
                            if (text != null && text.contains(find)) {
                                text = text.replace(find, replace);
                                r.setText(text, 0);
                            }
                        }
                    }
                }
            }
            saveDocument(doc, outpath);
        }
    }


    private XWPFDocument openDocument(String file) {
        XWPFDocument document = null;
        try {
            document = new XWPFDocument(OPCPackage.open(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return document;
    }

    private void saveDocument(XWPFDocument doc, String file) {
        try (FileOutputStream out = new FileOutputStream(file)) {
            doc.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}