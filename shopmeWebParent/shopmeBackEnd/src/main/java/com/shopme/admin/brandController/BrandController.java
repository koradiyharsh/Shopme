package com.shopme.admin.brandController;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.StringUtil;
import org.dom4j.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.DocumentException;
import com.shopme.admin.FileUploadUntil;
import com.shopme.admin.UserNotFoundExcepion;
import com.shopme.admin.Exporter.ExportCsvHandler;
import com.shopme.admin.users.category.CategoryService;
import com.shopme.common.entities.Brand;
import com.shopme.common.entities.Category;

@Controller
public class BrandController {

	@Autowired
	private BrandService brandService;
	
	@Autowired
	private CategoryService categoryService;
	
	
	@GetMapping("/brands")
	public String getBrandList(Model model , Brand brand)
	{
		/*
		 * List<Brand> Brands = this.brandService.getAllBrand();
		 * model.addAttribute("brands", Brands); return "brandview";
		 */
		
		return findByPage(1, model, "asc", "name", null);
	}
	
	@GetMapping("/brands/page/{pageNum}")
	public String findByPage(@PathVariable("pageNum") Integer pageNo , Model model , @Param("sortDir") String sortDir, 
		@Param("sortField") String sortField , @Param("keyword") String keyword)
	{
		
		
		Page<Brand> findBypage = this.brandService.findByPage(pageNo, sortField, sortDir, keyword);
		List<Brand> listofBrands = findBypage.getContent();
		
		
		long startCount = (pageNo - 1)  * this.brandService.BRAND_PAR_PAGE + 1;
		long endcount  = startCount + this.brandService.BRAND_PAR_PAGE - 1;
		if(endcount > findBypage.getNumberOfElements()) {
			endcount  =  findBypage.getTotalElements();
		}
		
		String reverseSortDir =  sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("brands", listofBrands);
		model.addAttribute("currentpage", pageNo);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endcount", endcount);
		model.addAttribute("totalcount", findBypage.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("totalpage", findBypage.getTotalPages());
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		return "brandview";
	}
	
	@GetMapping("/brands/new")
	public String CreateNewBrandForm(Model model)
	{
	List<Category> listCategoriesUsedInForm = this.categoryService.listCategoriesUsedInForm();
	model.addAttribute("listcategory", listCategoriesUsedInForm);
	model.addAttribute("pageTitle", "Create New Brand");
	model.addAttribute("brand", new Brand());	
	return "brand_form";	
	}
	
	@PostMapping("/brands/save")
	public String saveBrand(Brand brand, @RequestParam("fileimage") MultipartFile multipartFile
			, RedirectAttributes redirectAttributes) throws IOException {
		
			if(!multipartFile.isEmpty())
			{
		
			String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
			System.out.println("Get Original File Name "+fileName);
			brand.setLogo(fileName);
			Brand savedBrand = brandService.saveBrand(brand);
			String uploadDir = "../brand-logos/" + savedBrand.getId();
			System.out.println("Upload Dir "+uploadDir);
			FileUploadUntil.CleanDirectory(uploadDir);
			FileUploadUntil.saveFile(uploadDir, fileName, multipartFile);
			}
			else {
				
				this.brandService.saveBrand(brand);
				
			}
			redirectAttributes.addFlashAttribute("message","Successfully saved the Brand with ID "+brand.getId());
			return "redirect:/brands";		
	}
	
	
	
	@GetMapping("/brands/update/{id}")
	public String UpdateBrand(@PathVariable("id") Integer id , Model model , RedirectAttributes redirectAttributes)
	{
		
		try {
			Brand brand = this.brandService.getByBrandID(id);
			List<Category> listCategoriesUsedInForm = this.categoryService.listCategoriesUsedInForm();
			model.addAttribute("brand", brand);
			model.addAttribute("listcategory", listCategoriesUsedInForm);
			model.addAttribute("pageTitle", "Edit Brand ( ID : "+id+" )");
			return "brand_form";
		} catch (BrandNotFoundException ex) 
		{
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/brands";
		}
		
		
	}
	
	@GetMapping("/brands/delete/{id}")
	public String DeleteBrand(@PathVariable("id") Integer id , Model model , RedirectAttributes redirectAttributes)
	{
		try {
		
			
			this.brandService.DeleteById(id);
			String uploadDir = "../brand-logos/"+id;
			FileUploadUntil.removeDir(uploadDir);
			redirectAttributes.addFlashAttribute("message", "The brand ID "+id+" has been deleted successfully");
			
			
		} catch (BrandNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		
		return "redirect:/brands";
			
	}
	
	@GetMapping("/brands/export/csv")
	public void exportcsv(HttpServletResponse response) throws IOException
	{
		ExportCsvHandlerBrand exportCsvHandler = new ExportCsvHandlerBrand();
		List<Brand> allBrand = this.brandService.getAllBrand();
		exportCsvHandler.exportCsv(allBrand, response);
	}
	
	@GetMapping("/brands/export/pdf")
	public void exportpdf(HttpServletResponse response) throws DocumentException, IOException
	{
		
		ExportpdfHandlerBrand exportCsvHandlerBrand =  new ExportpdfHandlerBrand();
		List<Brand> allBrand = this.brandService.getAllBrand();
		exportCsvHandlerBrand.exportPDF(allBrand , response);
	}
	
	@GetMapping("/brands/export/excel")
	public void exportexcel(HttpServletResponse response) throws IOException
	{
		ExportExcelHandler excelHandler = new ExportExcelHandler();
		List<Brand> listBrand = this.brandService.getAllBrand();
		excelHandler.exportExcel(listBrand, response);
		
	}
	

	
	
	
	
	
	
	
	
	
	
}
