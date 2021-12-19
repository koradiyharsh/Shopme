package com.shopme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.product.productRepository;
import com.shopme.common.entities.Brand;
import com.shopme.common.entities.Category;
import com.shopme.common.entities.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class prducyRepositioryTest {

	@Autowired
	private productRepository productRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void ProductTest()
	{
			
		Brand brand  =  entityManager.find(Brand.class, 18);
		Category category  = entityManager.find(Category.class, 21);
		Product product = new Product();
		product.setName("harsh_Inspirati");
		product.setAlise("harsh_Le_Inspiration");
		product.setShortDescription("A good Apple like macbook Leptops");
		product.setFullDescription("A good Apple leptops with full descriptions");
		product.setBrand(brand);
		product.setCategory(category);
		product.setPrice(4000);
		product.setCreatedTime(new Date());
		product.setUpdatedTime(new Date());
		Product save = this.productRepository.save(product);
		assertThat(save).isNotNull();
		assertThat(save.getId()).isGreaterThan(0);
		
		
		
	}
	
	@Test
	public void TestListAllBrands()
	{	
		List<Product> findAll = (List<Product>)this.productRepository.findAll();
		findAll.forEach(product->System.out.println(product));
		assertThat(findAll.size()).isEqualTo(3);
	}
	
	@Test
	public void testGetProducts()
	{
		Integer id  =  2;
		Product product = this.productRepository.findById(id).get();
		System.out.println(product);
		assertThat(product).isNotNull();
	}
	
	@Test
	public void TestUpdateProducts()
	{
		Integer id  =  2;
		String name  = "Dell_Harsh";
		Product product = this.productRepository.findById(id).get();
		product.setName(name);
		assertThat(product.getName().equals(name));
		
		
	}
	
	@Test
	public void TestDeleteProducts()
	{
		int id = 3;
		Optional<Product> result = this.productRepository.findById(id);
		assertThat(!result.isPresent());
	}
	
	
	
	
}
