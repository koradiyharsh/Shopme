package com.shopme.admin.Exporter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopme.common.entities.Category;
import com.shopme.common.entities.User;

public class ExportCategoryCSV {

	
	public void ExportCategorycsvmethod(Iterable<Category> cate , HttpServletResponse response) throws IOException 
	{
		DateFormat dateformate =  new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		String timestamp  = dateformate.format(new Date());
		String filename  =  "Category_"+timestamp+".csv";
		response.setContentType("text/csv");
		String headerKey  = "Content-Disposition";
		String headerValue = "attachment;filename="+filename;
		
		response.setHeader(headerKey, headerValue);
		ICsvBeanWriter csvWriter  =  new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String csvHeader[] = {"Category ID" , "Category Name"  , "Category Alise"  ,"Enable"};
		String csvMapping[] = {"id" , "name"  , "alise" ,"enable"};
		
		csvWriter.writeHeader(csvHeader);
		cate.forEach(c -> {
			try {
				csvWriter.write(c, csvMapping);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} );
		
		csvWriter.close();
	}
}
