package com.shopme.admin.users;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.entities.Category;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer>  
{
    
	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
	public List<Category> listRootCategories(Sort sort);
	
	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
	public Page<Category> listRootCategories(Pageable sort);
	
	@Query("SELECT c FROM Category c WHERE c.name LIKE %?1%")
	public Page<Category> search(String keyword , Pageable pageable);
	
	public Category findByName(String name);
	
	public Category findByAlise(String alise);
	
	public Long countById(Integer id);

	
}
