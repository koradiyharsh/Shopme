package com.shopme.admin.brandController;

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
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.common.entities.Brand;
import com.shopme.common.entities.User;

public class ExportpdfHandlerBrand {

	public void exportPDF(List<Brand> allBrand, HttpServletResponse response) throws DocumentException, IOException 
	{
		DateFormat dateformate =  new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		String timestamp  = dateformate.format(new Date());
		String filename  =  "Brands_"+timestamp+".pdf";
		response.setContentType("application/pdf");
		String headerKey  = "Content-Disposition";
		String headerValue = "attachment;filename="+filename;
		Document document  = new Document(PageSize._11X17);
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(0, 0, 255);
		Paragraph paragraph = new Paragraph("List Of Brands", font);
		System.out.println();
		System.out.println();
		paragraph.setAlignment(paragraph.ALIGN_CENTER);
		
		document.add(paragraph);
		PdfPTable table  =  new PdfPTable(6);
		table.setWidthPercentage(100f);
		table.setSpacingBefore(10);
		writeTableHeader(table);
		writeTableBody(table , allBrand);
		document.add(table);
		document.close();

 	}

	private static void writeTableBody(PdfPTable table, List<Brand> Brands) 
	{
		for(Brand b  :  Brands)
		{
			
			table.addCell(String.valueOf(b.getId()));
			table.addCell(String.valueOf(b.getName()));
			table.addCell(String.valueOf(b.getCategories()));
			
			
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
		cell.setPhrase(new Phrase("Brand ID", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Brand Name", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Brand Categories", font));
		table.addCell(cell);
		
		
	
		
	}

}
