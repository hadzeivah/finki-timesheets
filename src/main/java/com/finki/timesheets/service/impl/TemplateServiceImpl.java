package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Project;
import com.finki.timesheets.model.Timesheet;
import com.finki.timesheets.service.TemplateService;
import com.finki.timesheets.service.TimesheetService;
import com.finki.timesheets.service.utils.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service(value = "templateService")
public class TemplateServiceImpl implements TemplateService {


    private static final String CLASS_PATH = "/templates/";
    private static final String FILE_DIRECTORY = "D:\\Ivan_Chorbev-Templates\\";
    private TimesheetService timesheetService;


    public TemplateServiceImpl(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @Override
    public ResponseEntity downloadAllTemplates(ArrayList<String> filenames, Project project) {

        String outpath = FILE_DIRECTORY + "documents.zip";
        coverLetterTemplate("coverLetter", project);
        requirementContractTemplate("requirement", project.getId());
        invoiceTemplate("invoice", project);
        solutionContractTemplate("solution", project.getId());

        ArrayList<String> filenamesAsDocx = new ArrayList<>();


        filenames.forEach(filename -> {
            switch (filename) {
                case "invoice":
                    invoiceTemplate(filename, project);
                case "solution":
                    solutionContractTemplate(filename, project.getId());
                case "requirement":
                    requirementContractTemplate(filename, project.getId());
                case "coverLetter":
                    coverLetterTemplate(filename, project);
            }
            filenamesAsDocx.add(filename + ".docx");
        });

        ZipMultipleFiles(outpath, filenamesAsDocx);
        return getResourceFromFileDirectory(outpath);
    }

    @Override
    public ResponseEntity getResourceFromFileDirectory(String filename) {
        Resource resource = new FileSystemResource(filename);
        String downloadFileName = resource.getFilename();

        if (resource.exists()) {
            try {
                InputStream fis = new FileInputStream(resource.getFile());
                InputStreamResource inputStreamResource = new InputStreamResource(fis);

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.documentrtg"))
                        .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Access-Control-Expose-Headers", "Content-Disposition")
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadFileName)
                        .body(inputStreamResource);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity coverLetterTemplate(String filename, Project project) {
        String filepath = CLASS_PATH + "Propratno06 - Copy.docx";
        String outpath = FILE_DIRECTORY + "coverLetter.docx";

        XWPFDocument doc = openDocument(filepath);
        HashMap<String, String> replacementValues = new HashMap<>();

        String from = StringUtils.formatDateToString_DDMMYYYY(project.getStartDate() != null ? project.getStartDate() : LocalDateTime.now());
        String to = StringUtils.formatDateToString_DDMMYYYY(project.getEndDate() != null ? project.getEndDate() : LocalDateTime.now());

        replacementValues.put("$$from$$", from);
        replacementValues.put("$$to$$", to);

        if (doc != null) {
            replaceText(doc, replacementValues, outpath);
        }
        return getResourceFromFileDirectory(outpath);
    }

    @Override
    public ResponseEntity invoiceTemplate(String filename, Project project) {
        String filepath = CLASS_PATH + "Faktura06 - Copy.docx";
        String outpath = FILE_DIRECTORY + "invoice.docx";

        XWPFDocument doc = openDocument(filepath);

        HashMap<String, String> replacementValues = new HashMap<>();
        replacementValues.put("$$dean$$", project.getUniversity().getDean());
        replacementValues.put("$$university$$", project.getUniversity().getName());
        replacementValues.put("$$project$$", project.getName());

        if (doc != null) {
            replaceCell(doc, replacementValues, outpath);
            replaceText(doc, replacementValues, outpath);
        }
        return getResourceFromFileDirectory(outpath);
    }

    @Override
    public ResponseEntity requirementContractTemplate(String filename, Long projectId) {
        String filepath = CLASS_PATH + "BaranjeTS6 - Copy.docx";
        String outpath = FILE_DIRECTORY + "requirement.docx";

        generateTimesheetTableByProject(projectId, filepath, outpath);
        return getResourceFromFileDirectory(outpath);
    }

    @Override
    public ResponseEntity solutionContractTemplate(String filename, Long projectId) {

        String filepath = CLASS_PATH + "Resenie06 - Copy.docx";
        String outpath = FILE_DIRECTORY + "solution.docx";

        generateTimesheetTableByProject(projectId, filepath, outpath);
        return getResourceFromFileDirectory(outpath);
    }

    private void generateTimesheetTableByProject(Long projectId, String filepath, String outpath) {
        XWPFDocument doc = openDocument(filepath);
        List<Timesheet> timesheets = timesheetService.findTimesheetsByProject(projectId);
        replaceTable(doc, timesheets, outpath);
    }

    private void replaceTable(XWPFDocument doc, List<Timesheet> timesheets, String outpath) {
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

    private void replaceText(XWPFDocument doc, HashMap<String, String> replacementValues, String outpath) {
        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    replace(replacementValues, r);

                }
            }
            saveDocument(doc, outpath);
        }
    }

    private void replaceCell(XWPFDocument doc, HashMap<String, String> replacementValues, String outpath) {
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            replace(replacementValues, r);
                        }
                    }
                }
            }
            saveDocument(doc, outpath);
        }
    }

    private void replace(HashMap<String, String> replacementValues, XWPFRun r) {
        String text = r.getText(0);
        if (text != null) {
            String replace = replacementValues.get(text.trim());
            if (replace != null) {
                text = text.replace(text, replace);
                r.setText(text, 0);
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

    private void saveDocument(XWPFDocument doc, String file) {
        try (FileOutputStream out = new FileOutputStream(file)) {
            doc.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ZipMultipleFiles(String outpath, ArrayList<String> srcFiles) {
        try {
            FileOutputStream fos = new FileOutputStream(outpath);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            for (String srcFile : srcFiles) {
                Resource fileToZip = new FileSystemResource(FILE_DIRECTORY + srcFile);
                FileInputStream fis = new FileInputStream(fileToZip.getFile());
                String filename = fileToZip.getFilename() != null ? fileToZip.getFilename() : "";
                ZipEntry zipEntry = new ZipEntry(filename);
                zipOut.putNextEntry(zipEntry);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
            }
            zipOut.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}