package com.shopme.admin.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.brandController.BrandService;
import com.shopme.admin.users.catController.categoryController;
import com.shopme.admin.users.category.CategoryService;
import com.shopme.common.entities.Brand;
import com.shopme.common.entities.Category;
import com.shopme.common.entities.Product;


@Controller
public class productController {

	@Autowired
	private productService productService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private CategoryService CategoryService;
	
	@GetMapping("/products")
	public String listproducts(Model model)
	{
		List<Product> getAllProducts = this.productService.GetAllProducts();
		model.addAttribute("listproduct", getAllProducts);
		return "product/listproducts";
		
	}
	
	@GetMapping("/products/new")
	public String product_form(Model model)
	{
		Product product = new Product();
		product.setEnabled(true);
		product.setIn_stock(true);
		List<Brand> listBrands = this.brandService.getAllBrand();
		model.addAttribute("listbrand", listBrands);
		model.addAttribute("product", product);
		model.addAttribute("pageTitle", "Create New Product");
		return "product/product_form";
	}
	
	@PostMapping("/products/save")
	public String productSaving(Product product , RedirectAttributes redirectAttributes)
	{
		
		
			this.productService.saveProduct(product);
			redirectAttributes.addFlashAttribute("message", "The product has been saved successfully");
			return "redirect:/products";
		
	}
	
	
	@GetMapping("/products/{id}/enabled/{status}")
	public String changeEnabledStatus(@PathVariable("id") Integer id , @PathVariable("status") boolean status , 
			RedirectAttributes RedirectAttributes)
	{
			boolean statusvalue  =  status;
			String tempstatus  = "";
			if(status==true) {
				tempstatus+="enabled";
			}
			else {
				tempstatus+="disabled";
			}
			Product productByID = this.productService.getProductByID(id);
			productByID.setEnabled(statusvalue);
			this.productService.saveProduct(productByID);
			RedirectAttributes.addFlashAttribute("message", "The Product ID "+id +" has been "+tempstatus);
			
			return "redirect:/products";
			
	}
	
	@GetMapping("/products/delete/{id}")
	public String DeleteByID(@PathVariable("id") Integer id , RedirectAttributes redirectAttributes)
	{
			try 
			{
				
				this.productService.delete(id);
				redirectAttributes.addFlashAttribute("message", "The Product ID "+id+" has been "
						+ "deleted Successfully");
			
			} catch (ProductNotFoundException ex) {
				
				 redirectAttributes.addFlashAttribute("message", ex.getMessage());	
			}
			
			return "redirect:/products";
	}
	


}
