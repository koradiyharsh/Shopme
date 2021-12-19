package com.shopme.admin.brand;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.entities.Brand;
import com.shopme.common.entities.Product;

@Repository
public interface BrandRepository extends PagingAndSortingRepository<Brand, Integer> {

	public Long countById(Integer id);
	
	public Brand findByName(String name);
	
	@Query("select b from Brand b WHERE b.name LIKE %?1%")
	public Page<Brand> findByKeyword(String name  , Pageable pageable);
	
	@Query("SELECT new Brand(b.id , b.name) FROM Brand b ORDER BY b.name asc")
	public List<Brand> findByKeyword();
	
	
	
	 
}
