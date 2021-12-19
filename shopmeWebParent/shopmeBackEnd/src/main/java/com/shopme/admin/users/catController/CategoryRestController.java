package com.shopme.admin.users.catController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.admin.users.category.CategoryService;

@RestController
public class CategoryRestController {

	@Autowired
	private CategoryService categoryService; 
	
	@PostMapping("/categories/check_unique")
	public String checkUniqueness(@Param("id") Integer id , @Param("name") String name ,
			@Param("alise") String alise)
	{
		return this.categoryService.checkUnique(id, name, alise);
	}
}
