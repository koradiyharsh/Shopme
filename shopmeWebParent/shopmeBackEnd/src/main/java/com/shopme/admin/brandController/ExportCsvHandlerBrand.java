package com.shopme.admin.brandController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopme.common.entities.Brand;

public class ExportCsvHandlerBrand {

	
public void exportCsv(List<Brand> brand , HttpServletResponse response) throws IOException
{	
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
			String formatstyle = dateFormat.format(new Date());
			String filename = "Brands_"+formatstyle+".csv";
			response.setContentType("text/csv");
			String headerKey  = "Content-Disposition";
			String headerValue = "attachment;filename="+filename;
			
			response.setHeader(headerKey, headerValue);
			ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			
			String csvHeader[] = {"Brand ID" , "Brand Name"  , "Categories"};
			String csvMapping[] = {"id" , "name"  , "categories"};
			
			beanWriter.writeHeader(csvHeader);
			
			for(Brand b : brand)
			{
				beanWriter.write(b, csvMapping);
			}
			
			
			beanWriter.close();
			
			
}
}
