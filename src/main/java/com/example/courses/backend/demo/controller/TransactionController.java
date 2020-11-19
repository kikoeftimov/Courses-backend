package com.example.courses.backend.demo.controller;

import com.example.courses.backend.demo.model.Transaction;
import com.example.courses.backend.demo.model.TransactionExcelExporter;
import com.example.courses.backend.demo.model.TransactionPdfExporter;
import com.example.courses.backend.demo.service.TransactionService;
import com.lowagie.text.DocumentException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> findAll(){
        List<Transaction> trans = this.transactionService.findAll();
        System.out.println(trans);
        return this.transactionService.findAll();
    }

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {

        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String fileName = "transactions_" + currentDateTime + ".xlsx";

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;

        response.setContentType("application/octet-stream");
        response.setHeader(headerKey, headerValue);

        List<Transaction> transactionList = transactionService.findAll();
        TransactionExcelExporter excelExporter = new TransactionExcelExporter(transactionList);
        excelExporter.export(response);
    }

    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transactions_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Transaction> transactionList = transactionService.findAll();

        TransactionPdfExporter transactionPdfExporter = new TransactionPdfExporter(transactionList);
        transactionPdfExporter.export(response);
    }
}
