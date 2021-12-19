package com.shopme.admin.Exporter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.common.usermodel.fonts.FontFamily;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.shopme.common.entities.User;

public class ExportExcelHandler 
{
	private XSSFWorkbook workbook;
	private  XSSFSheet stylesheet ;
	
	
	public ExportExcelHandler() {
		workbook  = new XSSFWorkbook();
	}
	
	public void writerHeaderLine()
	{
		stylesheet = workbook.createSheet("Users");
		XSSFRow createRow = stylesheet.createRow(0);
		XSSFCellStyle cellstyle  = workbook.createCellStyle();
		XSSFFont createFont = workbook.createFont();
		createFont.setBold(true);
		createFont.setFontHeight(12);
		cellstyle.setFont(createFont);
		
		createCell(createRow, 0 , "User ID", cellstyle);
		createCell(createRow, 1 , "E-Mail", cellstyle);
		createCell(createRow, 2 , "First Name", cellstyle);
		createCell(createRow, 3 , "Last Name", cellstyle);
		createCell(createRow, 4 , "Roles", cellstyle);
		createCell(createRow, 5 , "Enabled", cellstyle);
	}
	
	public  void createCell(XSSFRow row , int columnIndex , Object value , CellStyle style)
	{
		XSSFCell cell = row.createCell(columnIndex);
		stylesheet.autoSizeColumn(columnIndex);
		if(value instanceof Integer)
		{
			cell.setCellValue((Integer)value);
		}
		else if(value instanceof Boolean)
		{
			cell.setCellValue((Boolean)value);
		}
		else if(value instanceof String)
		{
			cell.setCellValue((String)value);
		}
		
		cell.setCellStyle(style);
		
		
	}
	public void exportExcel(List<User> listuser  , HttpServletResponse response) throws IOException
	{
		
		DateFormat dateformate =  new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		String timestamp  = dateformate.format(new Date());
		String filename  =  "Users_"+timestamp+".xlsx";
		response.setContentType("application/octet-stream");
		String headerKey  = "Content-Disposition";
		String headerValue = "attachment;filename="+filename;
		response.setHeader(headerKey, headerValue);
		
		
		
		
		writerHeaderLine();
		WriteDataContent(listuser);
		
		
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
		
		
		
		
		
		
		
		
		
	}

	private void WriteDataContent(List<User> listuser) {
	
		int RowNumber = 1;
		XSSFCellStyle cellstyle  = workbook.createCellStyle();
		XSSFFont createFont = workbook.createFont();
		createFont.setFontHeight(12);
		cellstyle.setFont(createFont);
		
		for( User user : listuser)
		{	
			XSSFRow row  =  stylesheet.createRow(RowNumber++);
			int colIndex = 0;
			createCell(row ,colIndex++ ,user.getId() , cellstyle);
			createCell(row ,colIndex++ ,user.getEmail() , cellstyle);
			createCell(row ,colIndex++ ,user.getFirstName() , cellstyle);
			createCell(row ,colIndex++ ,user.getLastName() , cellstyle);
			createCell(row ,colIndex++ ,user.getRoles().toString() , cellstyle);
			createCell(row ,colIndex++ ,user.isEnabled() , cellstyle);
			
		}
		
	}
}
