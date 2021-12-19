package com.shopme.admin.products;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.hibernate.internal.build.AllowSysOut;
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
public class productsTests 
{
	
	@Autowired
	private productRepository productrepositiory;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateProducts()
	{
		Brand brand  = this.entityManager.find(Brand.class, 14);
		Category category  =  this.entityManager.find(Category.class, 3);
		
	Product product = new Product();
	product.setName("Apple Galaxy A31");
	product.setAlise("Apple mac");
	product.setShortDescription("a good smartphone from Apple");
	product.setFullDescription("This is a very Good Apple full description");
	product.setBrand(brand);
	product.setCategory(category);
	product.setPrice(456);
	product.setIn_stock(true);
	product.setEnabled(true);
	product.setCreatedTime(new Date());
	product.setUpdatedTime(new Date());
	
	
	com.shopme.common.entities.Product saveproduct  =  this.productrepositiory.save(product);
	assertThat(saveproduct).isNotNull();
	assertThat(saveproduct.getId()).isGreaterThan(0);
	
	
	
	}
	
	@Test
	public void testListAllProducts()
	{	
				Iterable<Product> IterableProduct = this.productrepositiory.findAll();
				IterableProduct.forEach(System.out::println);
				
	}
	
	@Test
	public void testGetProduct()
	{
		Integer id  = 2;
		Product findById = this.productrepositiory.findById(id).get();
		System.out.println(findById);
		assertThat(findById).isNotNull();
		
		
	}
	@Test
	public void UpdateProduct()
	{
			Integer id  =  2;
			Product findById = this.productrepositiory.findById(id).get();
			findById.setName("Apple Galaxy A32");
			Product save = this.productrepositiory.save(findById);
			assertThat(save.getName()).isEqualTo("Apple Galaxy A32");
			
			
			
			
	}
	
	@Test
	public void DeleteTest()
	{
		Integer id  = 4;
		this.productrepositiory.deleteById(id);
		
		Optional<Product> ProductID = this.productrepositiory.findById(id);
		assertThat(!ProductID.isPresent());
		
		
		
		
	}
	
}
