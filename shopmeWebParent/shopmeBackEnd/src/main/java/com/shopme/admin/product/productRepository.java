package com.shopme.admin.product;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.entities.Product;

@Repository
public interface productRepository extends PagingAndSortingRepository<Product, Integer> {

	
	public Product findByName(String name);
	
	public Long countById(Integer id);
}
