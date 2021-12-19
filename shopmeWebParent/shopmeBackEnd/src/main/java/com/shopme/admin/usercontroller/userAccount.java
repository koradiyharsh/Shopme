package com.shopme.admin.usercontroller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUntil;
import com.shopme.admin.UserService;
import com.shopme.admin.security.shopmeUserDetails;
import com.shopme.common.entities.User;

@Controller
public class userAccount 
{
	
	@Autowired
	private UserService service;
	
	@GetMapping("/account")
	public String userAccountform(@AuthenticationPrincipal shopmeUserDetails loggedUser , Model model )
	{
		String email = loggedUser.getUsername();
		User user = this.service.getByemail(email);
		model.addAttribute("user", user);
		return "account_form";
	}
	
	@PostMapping("/account/update")
	public String saveUser(User user , RedirectAttributes redirectAttributes ,
@AuthenticationPrincipal shopmeUserDetails loggeduser , @RequestParam("image")  MultipartFile multipartFile ) throws IOException
	{
		
		
		
		System.out.println(multipartFile.getOriginalFilename());
		System.out.println(multipartFile.getName());
		
		if(!multipartFile.isEmpty())
		{
			
			String filename  =  org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhoto(filename);
			User uservar =  this.service.updateAccountDetails(user);
			String uploadDir  = "Upload_Dir/"+uservar.getId();
			FileUploadUntil.CleanDirectory(uploadDir);
			FileUploadUntil.saveFile(uploadDir, filename ,multipartFile);
			
		}
		else
		{	
			if(user.getPhoto()==null)
			{
				user.setPhoto(null);
				this.service.updateAccountDetails(user);
			}
			
		}
		loggeduser.setFirstName(user.getFirstName());
		loggeduser.setLastName(user.getLastName());
		redirectAttributes.addFlashAttribute("message","User updated  Succussfully !! Enjoy..."); //this.userService.saveUser(user);
		return "redirect:/account";
			/*
			 * String firstpart = user.getEmail().split("@")[0]; return
			 * "redirect:/users/page/1?sortField=id&sortDir=asc&keyword="+firstpart;
			 */
		 
	}
}
