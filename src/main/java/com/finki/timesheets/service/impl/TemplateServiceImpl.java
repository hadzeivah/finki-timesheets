package com.finki.timesheets.service.impl;

import com.finki.timesheets.model.Item;
import com.finki.timesheets.service.ItemService;
import com.finki.timesheets.service.TemplateService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "templateService")
public class TemplateServiceImpl implements TemplateService {


    private ItemService itemService;

    public TemplateServiceImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public void coverLetterTemplate() {
        String filepath = "C:\\Users\\pc\\Desktop\\Ivan_Chorbev-Templates\\Propratno06 - Copy.docx";
        String outpath = "C:\\Users\\pc\\Desktop\\Ivan_Chorbev-Templates\\Test.docx";

        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(OPCPackage.open(filepath));
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
        if (doc != null) {
            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null && text.contains("$$from$$")) {
                            text = text.replace("$$from$$", "03.05.2019");
                            r.setText(text, 0);
                        }
                        if (text != null && text.contains("$$to$$")) {
                            text = text.replace("$$to$$", "10.05.2019");
                            r.setText(text, 0);
                        }
                    }
                }
                try {
                    doc.write(new FileOutputStream(outpath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void invoiceTemplate() {

    }
    @Override
    public void requirementContractTemplate() {

    }

    @Override
    public void solutionContractTemplate() {

        String filepath = "C:\\Users\\pc\\Desktop\\Ivan_Chorbev-Templates\\Resenie06 - Copy.docx";

        XWPFDocument doc;
        try {
            doc = new XWPFDocument(OPCPackage.open(filepath));
            Optional<List<Item>> items = itemService.findItemsByTimesheet(1L);
            replaceTable(doc, items.orElse(new ArrayList<>()));
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }

    }


    private long replaceTable(XWPFDocument doc, List<Item> items) {
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
                }
                else
                {
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
                    for (int runPos = found.getBeginRun()+1; runPos <= found.getEndRun(); runPos++) {
                        XWPFRun partNext = runs.get(runPos);
                        partNext.setText("", 0);
                    }
                }
            }

        }

        fillTable(doc,table,items);
        return count;
    }

    private void fillTable(XWPFDocument doc, XWPFTable table, List<Item> items) {
        int currRow = 1;
        String outpath = "C:\\Users\\pc\\Desktop\\Ivan_Chorbev-Templates\\ResenieTest.docx";
        XWPFTableRow header  = table.getRow(0);
        header.getCell(0).setText("Бр.");
        header.addNewTableCell().setText("Име и презиме");
        header.addNewTableCell().setText("Матичен број\n" + "Трансакциска сметка\n");
        header.addNewTableCell().setText("Вид на активност");
        header.addNewTableCell().setText("Бр. час.");
        header.addNewTableCell().setText("€ \n" + "час\n");


        for(Item i : items){
            XWPFTableRow curRow = table.createRow();
            currRow++;
            curRow.getCell(0).setText(String.valueOf(currRow));
        }
        try {
            doc.write(new FileOutputStream(outpath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}