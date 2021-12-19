package com.shopme.admin.Exporter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.html.WebColors;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.common.entities.User;

public class ExportPDFHandler  {

	
	public static void exportPDF(List<User> listuser , HttpServletResponse response) throws DocumentException, IOException
	{
		DateFormat dateformate =  new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		String timestamp  = dateformate.format(new Date());
		String filename  =  "Users_"+timestamp+".pdf";
		response.setContentType("application/pdf");
		String headerKey  = "Content-Disposition";
		String headerValue = "attachment;filename="+filename;
		response.setHeader(headerKey, headerValue);
		
		Document document =  new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(0, 0, 255);
		Paragraph paragraph = new Paragraph("List Of Users", font);
		System.out.println();
		System.out.println();
		paragraph.setAlignment(paragraph.ALIGN_CENTER);
		
		document.add(paragraph);
		PdfPTable table  =  new PdfPTable(6);
		table.setWidthPercentage(100f);
		table.setSpacingBefore(10);
		writeTableHeader(table);
		writeTableBody(table , listuser);
		document.add(table);
		document.close();

 	}

	private static void writeTableBody(PdfPTable table, List<User> listuser) 
	{
		for(User user  :  listuser)
		{
			
			table.addCell(String.valueOf(user.getId()));
			table.addCell(String.valueOf(user.getEmail()));
			table.addCell(String.valueOf(user.getFirstName()));
			table.addCell(String.valueOf(user.getLastName()));
			table.addCell(String.valueOf(user.getRoles().toString()));
			table.addCell(String.valueOf(user.isEnabled()));
			
		}
		
	}

	private static void writeTableHeader(PdfPTable table) 
	{
		PdfPCell cell =  new PdfPCell();
		java.awt.Color baseColor = WebColors.getRGBColor("#0000FF");
		cell.setBackgroundColor(baseColor);
		cell.setPadding(5);
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(0 , 0 , 0);
		cell.setPhrase(new Phrase("User ID", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("E-mail", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("First Name", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Last Name", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Roles", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Enabled", font));
		table.addCell(cell);
		
		
	}
	
	
}
