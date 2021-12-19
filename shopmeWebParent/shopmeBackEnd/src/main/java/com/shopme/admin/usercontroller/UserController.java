package com.shopme.admin.usercontroller;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUntil;
import com.shopme.admin.UserNotFoundExcepion;
import com.shopme.admin.UserService;
import com.shopme.admin.Exporter.ExportCsvHandler;
import com.shopme.admin.Exporter.ExportExcelHandler;
import com.shopme.admin.Exporter.ExportPDFHandler;
import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;

import antlr.StringUtils;

@Controller
public class UserController {

	
	@Autowired
	private UserService userService;
	

	
	@GetMapping("/users/new")
	public String newUser(Model model)
	{
		User user =  new User();
		
		List<Role> allRoles = this.userService.getAllRole();
		model.addAttribute("user", user);
		model.addAttribute("pageTitle", "Create New User");
		user.setEnabled(true);
		model.addAttribute("allRoles", allRoles);
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user , RedirectAttributes redirectAttributes , @RequestParam("image")  MultipartFile multipartFile) throws IOException
	{
		
		
		
		
		
		if(!multipartFile.isEmpty())
		{
			
			String filename  =  org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhoto(filename);
			User uservar =  this.userService.saveUser(user);
			String uploadDir  = "Upload_Dir/"+uservar.getId();
			FileUploadUntil.CleanDirectory(uploadDir);
			FileUploadUntil.saveFile(uploadDir, filename ,multipartFile);
			
		}
		else
		{	
			if(user.getPhoto()==null)
			{
				user.setPhoto(null);
				this.userService.saveUser(user);
			}
			
		}
		
			redirectAttributes.addFlashAttribute("message","User Saved Succussfully !! Enjoy..."); //this.userService.saveUser(user);
			String firstpart  = user.getEmail().split("@")[0];
			return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword="+firstpart;
		 
	}
	
	@GetMapping("/users/edit/{id}")
	public String updateUser(@PathVariable("id") Integer id ,  RedirectAttributes redirectAttributes , Model model)
	{
		
		
		try 
		{
			
			User user =  this.userService.get(id);
			model.addAttribute("user", user);
			List<Role> allRole = this.userService.getAllRole();
			model.addAttribute("allRoles", allRole);
			model.addAttribute("pageTitle", "Edit User (ID :" + id + ")");
			return "user_form";
			
		} catch (UserNotFoundExcepion e) 
		{
			redirectAttributes.addFlashAttribute("message" , e.getMessage());
			return "redirect:/users";
			
		}
		
	}
	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id , RedirectAttributes redirectAttributes)
	{
			try {
				this.userService.DeleteUser(id);
				redirectAttributes.addFlashAttribute("message", "The User Have ID "+id+" Deleted Succussfully ! ");
			} catch (UserNotFoundExcepion ex) 
			{
				redirectAttributes.addFlashAttribute("message" , ex.getMessage());
			}
			
			return "redirect:/users";
	}
	
	@GetMapping("/users/{id}/enabled/{status}")
	public String enabledStatusChange(@PathVariable("id")  int id , @PathVariable("status") boolean status , RedirectAttributes redirectAttributes)
	{
		this.userService.UpdateEnabledStatus(id, status);
		String result  = status ? "enabled" : "disabled";
		String message  = "The User ID : " + id + " has been "+result;
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/users";
		
	}
	
	@GetMapping("/users")
	public String getFirstPage(Model model)
	{
		
		return getPageList(1, model , "firstName"  , "asc" , null);
		
	}
	
	@GetMapping("/users/page/{PageNumber}")
	public String getPageList(@PathVariable("PageNumber")   int pageNumber , Model model , @Param("sortField") String sortField , 
			@Param("sortDir") String sortDir , @Param("keyword") String keyword)
	{
		
		System.out.println("field Name is "+sortField); 
		System.out.println("Order is  "+sortDir);
		System.out.println("keyword is   "+keyword);
		Page<User> listAllPage = this.userService.listAllPage(pageNumber, sortField, sortDir , keyword);
		List<User> getAlluser = listAllPage.getContent();
		
		long startcount  =  (pageNumber - 1) * this.userService.PAGE_PER_USER + 1;
		long endcount  =  startcount +  this.userService.PAGE_PER_USER - 1;
		
		if(endcount > listAllPage.getTotalElements())
		{
			endcount =  listAllPage.getTotalElements();
		}
		String reverseDir = sortDir.equals("asc") ? "desc" : "asc";
		
		model.addAttribute("startcount", startcount);
		model.addAttribute("currentpage" , pageNumber);
		model.addAttribute("totalpage", listAllPage.getTotalPages());
		model.addAttribute("endcount", endcount);
		model.addAttribute("totalpagecount", listAllPage.getTotalElements());
		model.addAttribute("listUsers", getAlluser);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword" , keyword);
		model.addAttribute("reverseDir", reverseDir);
		
		return "users";
		
		
	}
	
	@GetMapping("/users/export/csv")
	public void ExportCSV(HttpServletResponse response) throws IOException
	{
		
		ExportCsvHandler  exportcsv  =  new ExportCsvHandler();
		List<User> listuser = this.userService.getAllUsers();
		exportcsv.exportCsv(listuser, response);
		
	}
	@GetMapping("/users/export/excel")
	public void ExportExcel(HttpServletResponse response) throws IOException
	{
		
		ExportExcelHandler exportExcel  = new ExportExcelHandler();
		List<User> listuser = this.userService.getAllUsers();
		exportExcel.exportExcel(listuser, response);
	}
	@GetMapping("/users/export/pdf")
	public void ExportPDF(HttpServletResponse response) throws IOException
	{
		
		ExportPDFHandler exportPDF  = new ExportPDFHandler();
		List<User> listuser = this.userService.getAllUsers();
		exportPDF.exportPDF(listuser, response);
	}
	
	
	
	
}
