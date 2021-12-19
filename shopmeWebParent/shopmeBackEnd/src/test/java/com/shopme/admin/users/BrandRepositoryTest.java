package com.shopme.admin.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.brand.BrandRepository;
import com.shopme.common.entities.Brand;
import com.shopme.common.entities.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTest {

	@Autowired
	private  BrandRepository brandRepository; 
	
	
	
	@Test
	public void testCreateBrand1()
	{
		Category category = new Category(6);
		Brand brand  = new Brand("acer");
		brand.getCategories().add(category);
		Brand save = this.brandRepository.save(brand);
		assertThat(save).isNotNull();
		assertThat(save.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testCreateBrand2()
	{
		
		Category category1 =  new Category(8);
		Category category2 =  new Category(21);
		
		List<Category> catelist=new ArrayList<>();
		catelist.add(category1);
		catelist.add(category2);
		Brand   brand2 = new Brand("Apple");
		brand2.getCategories().addAll(catelist);
		this.brandRepository.save(brand2)
;		assertThat(brand2).isNotNull();
		assertThat(brand2.getCategories().size()).isEqualTo(2);
		
		
		
	}
	
	@Test
	public void testBrand3()
	{
		Category category1  = new Category(25);
		Category category2  = new Category(30);
		
		Brand  brand3 = new Brand("samsung");
		brand3.getCategories().addAll(List.of(category1 , category2));
		this.brandRepository.save(brand3);
		assertThat(brand3).isNotNull();
		assertThat(brand3.getCategories().size()).isEqualTo(2);
		
	}
	
	@Test
	public void printAllbrand()
	{
			Iterable<Brand> brands = this.brandRepository.findByKeyword();
			brands.forEach(System.out::println);
			
			assertThat(brands).isNotNull();
	}
	@Test
	public void findById()
	{
			Integer id  =  1;
			Brand findById = this.brandRepository.findById(id).get();
			assertThat(findById.getId()).isEqualTo(1);
	}
	
	@Test
	public void updateBrand()
	{
		String name  = "Samsung Electronics";
		Brand findById = this.brandRepository.findById(4).get();	
		
		findById.setName(name);
		Brand save = this.brandRepository.save(findById);
		assertThat(save.getName()).isEqualTo(name);
		
	}
	@Test
	public void deleteBrand()
	{
			Integer id  = 4;
			this.brandRepository.deleteById(id);
			Optional<Brand> AfterDel = this.brandRepository.findById(id);
			assertThat(AfterDel.isEmpty());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
