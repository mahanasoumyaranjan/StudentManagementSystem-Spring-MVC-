package com.cglia.sms.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cglia.sms.model.Student;
import com.cglia.sms.service.StudentService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
public class PDFController {

	@Autowired
	private StudentService service;

	@GetMapping("/getpdf")
	public void downloadPDF(HttpServletResponse response) {
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=student.pdf");

		try {
			Document document = new Document();
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();

			Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.RED);
			Paragraph title = new Paragraph("Student List", titleFont);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);

			document.add(Chunk.NEWLINE);

			List<Student> student = service.getAllStudent();

			if (!student.isEmpty()) {
				PdfPTable table = new PdfPTable(4);
				float[] columnWidths = { 3f, 6f, 8f, 4f};
				table.setWidths(columnWidths);

				table.addCell(createHeaderCell("ID"));
				table.addCell(createHeaderCell("Name"));
				table.addCell(createHeaderCell("Email"));
				table.addCell(createHeaderCell("Department"));

				student.forEach(std -> {
					table.addCell(createCell(String.valueOf(std.getId())));
					table.addCell(createCell(std.getName()));
					table.addCell(createCell(std.getEmail()));
					table.addCell(createCell(std.getDept()));
				});

				document.add(table);
			} else {
				document.add(new Paragraph("Student list is empty"));
			}

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private PdfPCell createHeaderCell(String content) {
		PdfPCell cell = new PdfPCell(new Phrase(content));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		return cell;
	}

	private PdfPCell createCell(String content) {
		PdfPCell cell = new PdfPCell(new Phrase(content));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		return cell;
	}
}