package com.example.courses.backend.demo.service.impl;

import com.example.courses.backend.demo.model.Transaction;
import com.example.courses.backend.demo.repository.TransactionsRepository;
import com.example.courses.backend.demo.service.TransactionService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionsRepository transactionsRepository;

    public TransactionServiceImpl(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public List<Transaction> findAll() {
        return this.transactionsRepository.findAll();
    }

    @Override
    public byte[] exportToXlsx() {
//        List<Transaction> transactions = transactionsRepository.findAll();
//        try {
//            XSSFWorkbook workbook = new XSSFWorkbook();
//            File file = new File("Export.xlsx");
//            XSSFSheet sheet = workbook.createSheet("sheet1");// creating a blank sheet
//            int rownum = 0;
//
//            CellStyle cs = workbook.createCellStyle();
//            cs.setAlignment(CellStyle.ALIGN_CENTER);
//            cs.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
//            cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
//            Font font = workbook.createFont();
//            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
//            cs.setFont(font);
//            cs.setBorderBottom(CellStyle.BORDER_THIN);
//            cs.setBottomBorderColor(IndexedColors.BLACK.getIndex());
//            cs.setBorderLeft(CellStyle.BORDER_THIN);
//            cs.setLeftBorderColor(IndexedColors.BLACK.getIndex());
//            cs.setBorderRight(CellStyle.BORDER_THIN);
//            cs.setRightBorderColor(IndexedColors.BLACK.getIndex());
//            cs.setBorderTop(CellStyle.BORDER_THIN);
//            cs.setTopBorderColor(IndexedColors.BLACK.getIndex());
//            Row row = sheet.createRow(rownum++);
//            sheet.addMergedRegion(CellRangeAddress.valueOf("A1:A2"));
//            Cell cell = row.createCell(0);
//            cell.setCellValue("No.");
//            cell.setCellStyle(cs);
//
//            sheet.addMergedRegion(CellRangeAddress.valueOf("B1:B2"));
//            cell = row.createCell(1);
//            cell.setCellValue("EMAIL");
//            cell.setCellStyle(cs);
//
//            sheet.addMergedRegion(CellRangeAddress.valueOf("C1:C2"));
//            cell = row.createCell(2);
//            cell.setCellValue("AMOUNT");
//            cell.setCellStyle(cs);
//
//            sheet.addMergedRegion(CellRangeAddress.valueOf("D1:D2"));
//            cell = row.createCell(3);
//            cell.setCellValue("DATE");
//            cell.setCellStyle(cs);
//
//            sheet.addMergedRegion(CellRangeAddress.valueOf("E1:E2"));
//            cell = row.createCell(4);
//            cell.setCellValue("SHOPPING CART - STATUS");
//            cell.setCellStyle(cs);
//
//            sheet.addMergedRegion(CellRangeAddress.valueOf("F1:F2"));
//            cell = row.createCell(5);
//            cell.setCellValue("USERNAME");
//            cell.setCellStyle(cs);
//
//            rownum = 2;
//            int no = 1;
//            for (Transaction t : transactions) {
//                row = sheet.createRow(rownum++);
//                createList(t, row, workbook, no);
//                no++;
//            }
//            for (int i = 0; i < 19; i++) {
//                sheet.autoSizeColumn(i);
//            }
//            FileOutputStream out = new FileOutputStream(file); // file name with path
//            workbook.write(out);
//            out.close();
//            byte[] bytes = readFileToByteArray(file);
//            return bytes;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return new byte[0];
        return null;
    }

    @Override
    public void createList(Transaction transaction, Row row, XSSFWorkbook workbook, Integer no) {

    }
}
