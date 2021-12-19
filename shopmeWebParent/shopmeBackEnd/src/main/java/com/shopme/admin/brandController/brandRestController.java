package com.shopme.admin.brandController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.common.entities.Brand;
import com.shopme.common.entities.Category;

@RestController
public class brandRestController {

	@Autowired
	private BrandService brandService;
	
	@PostMapping("/brands/check_unique")
	public String checkUniqueValue(@Param("id") Integer id , @Param("name") String name)
	{
		
		String checkUniqueBrand = this.brandService.checkUniqueBrand(id, name);
		return checkUniqueBrand;
		
	}
	
	@GetMapping("/brands/{id}/categories")
	public List<CategoryDTO> listCategoriesDTO(@PathVariable("id") Integer brandID) throws BrandNotFoundRestException 
	{
		List<CategoryDTO> listcategoryDTO = new ArrayList<>();
		try {
			Brand brand = this.brandService.getByBrandID(brandID);
			Set<Category> categories = brand.getCategories();
			for(Category cat :  categories)
			{
				
				CategoryDTO categoryDTO = new CategoryDTO(cat.getId(), cat.getName());
				listcategoryDTO.add(categoryDTO);
			}
			
			return listcategoryDTO;
		} catch (BrandNotFoundException e) {

			throw new BrandNotFoundRestException();
		}
		
		
	}
	
	
	
}
