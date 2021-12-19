package com.shopme.admin.users.catController;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUntil;
import com.shopme.admin.Exporter.ExportCategoryCSV;
import com.shopme.admin.users.CategoryRepository;
import com.shopme.admin.users.category.CategoryNotFountException;
import com.shopme.admin.users.category.CategoryService;
import com.shopme.common.entities.Category;

@Controller
public class categoryController {

	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	
	
	@GetMapping("/categories")
	public String getAllCategories(@Param("sortDir") String sortDir, Model model)
	{
		
		return listByPage(1 , sortDir,  model , null) ;
	}
	
	
	
	@GetMapping("/categories/page/{pageNumber}")
	public String listByPage(@PathVariable("pageNumber") Integer pagenumber , @Param("sortDir") 
	String sortDir, Model model , @Param("keyword") String keyword) 
	{
		
		if(sortDir==null || sortDir.isEmpty())
		{
			sortDir = "asc";
		}
		
		
		long startcount  =  (pagenumber - 1) * this.categoryService.ROOT_CATEGORY_PER_PAGE + 1;
		long endcount  =  startcount +  this.categoryService.ROOT_CATEGORY_PER_PAGE - 1;
		
		CategoryperPage pageinfo = new CategoryperPage();
		List<Category> allCategories = this.categoryService.getAllCategories(pageinfo, pagenumber, sortDir , keyword);
		String reverseOrder = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseDir",reverseOrder );
		model.addAttribute("listCategories", allCategories);
		model.addAttribute("sortField", "name");
		model.addAttribute("totalpage", pageinfo.getTotalpage());
		model.addAttribute("keyword", keyword);
		model.addAttribute("startcount", startcount);
		model.addAttribute("endcount", endcount);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("totalItems", pageinfo.getTotalElements());
		model.addAttribute("totalpagecount", pageinfo.getTotalElements());
		model.addAttribute("currentpage" , pagenumber);
		return "view_category";
		
	}
	
	@GetMapping("/categories/new")
	public String categoryForm(Model model)
	{
		
		List<Category> listCategoriesUsedInForm = this.categoryService.listCategoriesUsedInForm();
		model.addAttribute("category"  , new Category());
		model.addAttribute("listCategories" , listCategoriesUsedInForm);
		model.addAttribute("pageTitle", "Create new Category");
		return "category_form";
	}
	
	@PostMapping("/categories/save")
	public String saveCategories(Category category , @RequestParam("fileimage") MultipartFile multipartfile , RedirectAttributes redirectAttribute) throws IOException
	{
		if(!multipartfile.isEmpty())
		{
			
		
			
		
		
		String fileName = StringUtils.cleanPath(multipartfile.getOriginalFilename());
		
		category.setImage(fileName);
		Category categorysaved = this.categoryService.saveCategory(category);
		
		String uploadDir  = "../category_image/"+categorysaved.getId();
		FileUploadUntil.CleanDirectory(uploadDir);
		FileUploadUntil.saveFile(uploadDir, fileName, multipartfile);
		}
		else {
			this.categoryService.saveCategory(category);
		}
		redirectAttribute.addFlashAttribute("categorysaved", "Category has been saved successfully!!");
		return "redirect:/categories";
	}
	
	@GetMapping("/categories/update/{id}")
	public String UpdateCategory(@PathVariable("id")  Integer id , Model model, RedirectAttributes redirectAttributes)
	{
		
		
		try {
			Category category = this.categoryService.UpdateCategoryById(id);
			List<Category> parentCategoyList = this.categoryService.getParentCategoyList();
			model.addAttribute("category", category);
			model.addAttribute("listCategories", parentCategoyList);
			model.addAttribute("pageTitle", "Update Category ( "+category.getId()+" )");
			return "category_form";
		} catch (CategoryNotFountException ex) {
			redirectAttributes.addFlashAttribute("categorysaved", ex.getMessage());
			return "redirect:/categories";
		}
	
	}
	
	@GetMapping("/categories/{id}/enable/{status}")
	public String UpdateStatus(@PathVariable("id") Integer id , @PathVariable("status") String status , RedirectAttributes redirectAttributes)
	{
		
		Category category = this.categoryService.getCategoryByID(id);
		String msg = "";
		if(status.equals("false")) 
		{
			category.setEnable(false);
			msg+="Disable";
		}
		else {
			category.setEnable(true);
			msg+="Enabled";
		}
		this.categoryService.saveCategory(category);
		String statusmsg = "The category ID " + id + " has been " + msg;
		redirectAttributes.addFlashAttribute("categorysaved", statusmsg);
		return "redirect:/categories";
	}
	
	@GetMapping("/categories/delete/{id}")
	public String deletecategory(@PathVariable("id") Integer id , RedirectAttributes redirectAttributes)
	{
	
		try {
			
			this.categoryService.DeleteCategoryById(id);
			String categoryDir  = "../category_image/"+id;
			FileUploadUntil.removeDir(categoryDir);
			redirectAttributes.addFlashAttribute("categorysaved" , "User with user ID " + id+ " Deleted Successfully");
		} catch (CategoryNotFountException ex) {
			redirectAttributes.addFlashAttribute("categorysaved", ex.getMessage());
			
		}
		
		return "redirect:/categories";
		
		
	}
	@GetMapping("/categories/export/csv")
	public void ExportCSV(HttpServletResponse response) throws IOException
	{
	
		ExportCategoryCSV exportcategory =  new ExportCategoryCSV();
		Iterable<Category> category = this.categoryRepository.findAll();
		exportcategory.ExportCategorycsvmethod(category, response);
	}
}
