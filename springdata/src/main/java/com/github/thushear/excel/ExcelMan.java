package com.github.thushear.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kongming on 2017/10/27.
 */
public class ExcelMan {

    public static void main(String[] args) throws IOException {
//        Workbook workbook = new HSSFWorkbook();
//        Sheet sheet = workbook.createSheet("new sheet");
//        workbook.createSheet("second sheet");
//        Row row = sheet.createRow(0);
//        Cell cell = row.createCell(0);
//        cell.setCellValue("name");
//        row.createCell(1).setCellValue("age");
//        FileOutputStream fileOutputStream = new FileOutputStream("E:\\workbook.xls");
//        workbook.write(fileOutputStream);
//        fileOutputStream.close();
//
//        Workbook workbookNew = new XSSFWorkbook();
//        workbookNew.createSheet("new sheet");
//        workbookNew.createSheet("second sheet");
//        FileOutputStream fileOutputStreamNew = new FileOutputStream("E:\\workbook.xlsx");
//        workbookNew.write(fileOutputStreamNew);
//        fileOutputStreamNew.close();

        testCreateCells();
        differentCells();


    }

    public static void testCreateCells() throws IOException {
        Workbook wb = new HSSFWorkbook();
        //Workbook wb = new XSSFWorkbook();
        CreationHelper  createHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("new sheet");

        // Create a row and put some cells in it. Rows are 0 based.
        Row row = sheet.createRow(0);

        // Create a cell and put a date value in it.  The first cell is not styled
        // as a date.
        Cell cell = row.createCell(0);
        cell.setCellValue(new Date());

        // we style the second cell as a date (and time).  It is important to
        // create a new cell style from the workbook otherwise you can end up
        // modifying the built in style and effecting not only this cell but other cells.
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
        cell = row.createCell(1);
        cell.setCellValue(new Date());
        cell.setCellStyle(cellStyle);

        //you can also set date as java.util.Calendar
        cell = row.createCell(2);
        cell.setCellValue(Calendar.getInstance());
        cell.setCellStyle(cellStyle);

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("E:\\workbook.xls");
        wb.write(fileOut);
        fileOut.close();
    }


    public static void differentCells() throws IOException {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");
        Row row = sheet.createRow((short)2);
        row.createCell(0).setCellValue(1.1);
        row.createCell(1).setCellValue(new Date());
        row.createCell(2).setCellValue(Calendar.getInstance());
        row.createCell(3).setCellValue("a string");
        row.createCell(4).setCellValue(true);
//        row.createCell(5).setCellType(CellType.ERROR);

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("E:\\wb.xls");
        wb.write(fileOut);
        fileOut.close();
    }


}
