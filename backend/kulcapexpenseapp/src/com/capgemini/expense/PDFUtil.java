package com.capgemini.expense;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;

import org.apache.commons.codec.binary.Base64;

import com.capgemini.expense.model.Employee;
import com.capgemini.expense.model.Expense;
import com.capgemini.expense.model.ExpenseForm;
import com.capgemini.expense.model.ExpenseLocation;
import com.capgemini.expense.model.ExpenseType;
import com.capgemini.expense.model.Status;
import com.capgemini.expense.model.Unit;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFUtil {

	public static void generatePdf(ExpenseForm form, Employee emp, OutputStream out) throws DocumentException {
		// step 1
		Document document = new Document(PageSize.LETTER.rotate());
		// step 2
		PdfWriter.getInstance(document, out);
		// step 3
		document.open();
		Font fontbold = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
		// step 4
		document.add(new Paragraph("Expense note from: " + emp.getLastName() + ", " + emp.getFirstName() + " - emp°: " + emp.getEmployeeNumber() + " - unit: "
				+ Unit.getById(emp.getUnitId()), fontbold));
		document.add(Chunk.NEWLINE);
		document.add(new Paragraph("Month: " + form.getDate().getMonth() + ", year: " + (form.getDate().getYear() + 1900)));
		document.add(Chunk.NEWLINE);
		document.add(new Paragraph("Current State: " + Status.getById(form.getStatusId())));
		document.add(Chunk.NEWLINE);

		// a table with three columns
		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100);

		table.addCell(new Phrase("Date", fontbold));
		table.addCell(new Phrase("Projectcode", fontbold));
		table.addCell(new Phrase("Where", fontbold));
		table.addCell(new Phrase("Type", fontbold));
		table.addCell(new Phrase("Amount", fontbold));
		table.addCell(new Phrase("Currency", fontbold));
		table.addCell(new Phrase("Remark", fontbold));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		for (Expense expense : form.getExpenses()) {
			table.addCell(sdf.format(expense.getDate()));
			table.addCell(expense.getProjectCode());
			table.addCell(ExpenseLocation.getById(expense.getExpenseLocationId()).toString());
			table.addCell(ExpenseType.getById(expense.getExpenseTypeId()).toString());
			table.addCell(Double.toString(expense.getAmount()));
			table.addCell(expense.getCurrency());
			table.addCell(expense.getRemarks());
		}
		document.add(table);
		document.add(Chunk.NEWLINE);
		document.add(new Paragraph("General remarks: " + form.getRemarks()));
		document.add(Chunk.NEWLINE);

		byte[] decodedBytes = Base64.decodeBase64(form.getSignature());

		Image img;
		try {
			img = Image.getInstance(decodedBytes);
			if (img.getScaledWidth() > 300 || img.getScaledHeight() > 300) {
				img.scaleToFit(300, 300);
			}
			document.add(img);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		document.add(Chunk.NEWLINE);
		document.add(new Paragraph("Evidences (see below)", fontbold));

		for (Expense expense : form.getExpenses()) {
			decodedBytes = Base64.decodeBase64(expense.getEvidence());
			Image evidenceImg;
			try {
				evidenceImg = Image.getInstance(decodedBytes);
				if (evidenceImg.getScaledWidth() > 500 || evidenceImg.getScaledHeight() > 500) {
					evidenceImg.scaleToFit(500, 500);
				}
				document.add(evidenceImg);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// step 5
		document.close();

	}

}
