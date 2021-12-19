package com.shopme.admin.product;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entities.Product;

@Service
public class productService {

	
	@Autowired
	private productRepository productRepository;
	
	public List<Product> GetAllProducts()
	{
		List<Product> findAll = (List<Product>) this.productRepository.findAll();
		return findAll;
	}
	
	public Product saveProduct(Product product)
	{
		
		if(product.getId() == null)
		{
			product.setCreatedTime(new Date());
			
		}
		if(product.getAlise()==null ||  product.getAlise().isEmpty())
		{
			
			String deafultAlise  = product.getName().replaceAll(" ", "-");
			product.setAlise(deafultAlise);
		}
		else {
			product.setAlise(product.getName().replaceAll(" ", "-"));
		}
		
		product.setUpdatedTime(new Date());
		return this.productRepository.save(product);
	}
	
	public String checkProductUnique(Integer id , String name)
	{
		
				boolean isCreatedNew  =  (id == null || id==0);
				Product productByName = this.productRepository.findByName(name);
				if(isCreatedNew)
				{
					System.out.println("I am in first phase");
					if(productByName!=null) {
						return "Duplicate";
					}
				}
				else {
						
					System.out.println("I am in second phase");
					if(productByName!=null && productByName.getId() != id) {
						return "Duplicate";
					}
					
				}
				System.out.println("I am in third phase");
			return "OK";
	}
	
	public Product getProductByID(Integer id)
	{
		return this.productRepository.findById(id).get();
	}
	public void delete(Integer id) throws ProductNotFoundException
	{
		
			if(id==null  || id==0) {
				throw new ProductNotFoundException("Could not find any product with ID: "+id);
			}
			
			this.productRepository.deleteById(id);
	}
	
}
