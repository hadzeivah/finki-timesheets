package com.finki.timesheets.service.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtils {


    Cell wrapTextInCell(Cell cell, Workbook wb) {
        CellStyle cs = wb.createCellStyle();
        cs.setWrapText(true);
        cell.setCellStyle(cs);
        return cell;
    }

}
