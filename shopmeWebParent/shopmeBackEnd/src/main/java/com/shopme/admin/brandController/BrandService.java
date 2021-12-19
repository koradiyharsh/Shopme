package com.shopme.admin.brandController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.admin.brand.BrandRepository;
import com.shopme.common.entities.Brand;

@Service
public class BrandService {

	
	public static final int BRAND_PAR_PAGE = 4;
	
	@Autowired
	private BrandRepository brandrepository;
	
	public List<Brand> getAllBrand()
	{
		List<Brand> findAll =  (List<Brand>) this.brandrepository.findAll();
		return findAll;
	}
	
	public Page<Brand> findByPage(int pagenumber , String sortField , String sortDir , String keyword )
	{
		
		Sort sort = Sort.by(sortField);
		
		sort =  sortDir.equals("asc")?sort.ascending():sort.descending();
		
		Pageable pageable =  PageRequest.of(pagenumber - 1, BRAND_PAR_PAGE , sort);
		
		Page<Brand> page=null;
		if(keyword!=null) {
			page= this.brandrepository.findByKeyword(keyword, pageable);
		}
		else {
			page  =  this.brandrepository.findAll(pageable);
		}
		
		 return page;
		
	}
	
	public Brand saveBrand(Brand brand)
	{
		Brand save = this.brandrepository.save(brand);
		return save;
	}
	
	public Brand getByBrandID(Integer id) throws BrandNotFoundException 
	{
		
		try 
		{
			return this.brandrepository.findById(id).get();
				
		} catch (NoSuchElementException e) {
			throw new  BrandNotFoundException("Brand not found with this ID "+id);
		}
	}
	public void DeleteById(Integer ID) throws BrandNotFoundException
	{
		Long countByID = this.brandrepository.countById(ID);
		
			if(countByID == null || countByID==0) {
				throw new BrandNotFoundException("Brand not found with this ID "+ID);
			}
		this.brandrepository.deleteById(ID); 
		
	}
	public String checkUniqueBrand(Integer id ,  String name)
	{
		boolean isCreateNew = (id==null || id==0);
		Brand brandByName = this.brandrepository.findByName(name);
		if(isCreateNew)
		{
			if(brandByName!=null) {
				return "Duplicate";	
			}
				
		}
		else {
			if(brandByName!=null  &&  id != brandByName.getId()) {
				return "Duplicate";
			}
			
		}
		return "OK";
	}

}
