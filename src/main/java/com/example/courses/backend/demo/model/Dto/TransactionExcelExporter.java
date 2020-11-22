package com.example.courses.backend.demo.model.Dto;

import com.example.courses.backend.demo.model.Transaction;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TransactionExcelExporter {
    private XSSFWorkbook workbook;

    private XSSFSheet sheet;

    private List<Transaction> transactionList;

    public TransactionExcelExporter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Transactions");
    }

    private void writeHeaderRow(){
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        Cell cell = row.createCell(0);
        cell.setCellValue("Transaction ID");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("E-mail");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("Amount");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("Date of purchase");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("Shopping cart status");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("Username");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("Courses");
        cell.setCellStyle(style);
    }

    private void writeDataRows(){
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for(Transaction transaction : transactionList){
            Row row = sheet.createRow(rowCount++);

            Cell cell = row.createCell(0);
            cell.setCellValue(transaction.getId());
            sheet.autoSizeColumn(0);
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(transaction.getUser().getEmail());
            sheet.autoSizeColumn(1);
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(transaction.getAmount() + "$");
            sheet.autoSizeColumn(2);
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(transaction.getLocalDateTime());
            sheet.autoSizeColumn(3);
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(transaction.getShoppingCart().getStatus().toString());
            sheet.autoSizeColumn(4);
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(transaction.getUser().getUsername());
            sheet.autoSizeColumn(5);
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue(transaction.getNames().toString());
            sheet.autoSizeColumn(6);
            cell.setCellStyle(style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRows();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
