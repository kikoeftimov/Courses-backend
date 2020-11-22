package com.example.courses.backend.demo.model.Dto;

import com.example.courses.backend.demo.model.Transaction;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class TransactionPdfExporter {

    private List<Transaction> transactionList;

    public TransactionPdfExporter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(6);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Transaction ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Amount", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date of purchase", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Shopping cart status", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Username", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Courses", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        for (Transaction transaction : transactionList) {
            table.addCell(transaction.getId().toString());
            table.addCell(transaction.getUser().getEmail());
            table.addCell(transaction.getAmount().toString() + "$");
            table.addCell(transaction.getLocalDateTime());
            table.addCell(transaction.getShoppingCart().getStatus().toString());
            table.addCell(transaction.getUser().getUsername());
            table.addCell(transaction.getNames().toString());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of Transactions", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 1.5f, 2.5f, 2.0f, 2.0f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
