package com.shopme.admin.Exporter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopme.common.entities.User;



public class ExportCsvHandler {
	
	public void exportCsv(List<User> listuser  , HttpServletResponse response) throws IOException
	{
		
		DateFormat dateformate =  new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		String timestamp  = dateformate.format(new Date());
		String filename  =  "Users_"+timestamp+".csv";
		response.setContentType("text/csv");
		String headerKey  = "Content-Disposition";
		String headerValue = "attachment;filename="+filename;
		
		response.setHeader(headerKey, headerValue);
		
		ICsvBeanWriter csvWriter  =  new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String csvHeader[] = {"User ID" , "E-mail"  , "First Name"  ,"Last Name" ,"Roles" , "Enabled"};
		String csvMapping[] = {"id" , "email"  , "firstName" ,"lastName" ,"roles" , "enabled"};
		csvWriter.writeHeader(csvHeader);
		for(User user  : listuser)
		{
			csvWriter.write(user, csvMapping);
		}
		csvWriter.close();
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
}
