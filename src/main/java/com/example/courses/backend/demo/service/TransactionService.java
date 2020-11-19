package com.example.courses.backend.demo.service;

import com.example.courses.backend.demo.model.Transaction;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public interface TransactionService {

    List<Transaction> findAll();

    byte[] exportToXlsx();

    void createList(Transaction transaction, Row row, XSSFWorkbook workbook, Integer no);
}
