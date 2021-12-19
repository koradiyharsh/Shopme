package com.shopme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.charThat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.users.CategoryRepository;
import com.shopme.common.entities.Category;


@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class CategoryRepositoryTest 
{
		
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Test
	public void CreateCategory()
	{
		
		Category category =  new Category("Electronic");
		Category savedcategory = categoryRepository.save(category);
		assertThat(savedcategory.getId()).isGreaterThan(0);
		
		
	}
	@Test
	public void CreateSubCategories()
	{
		Category parent = new Category(8);
		Category category  =  new Category("iPhone ", parent);
		
		Category save = this.categoryRepository.save(category);
		assertThat(save.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void getCategoryTest()
	{
		Category category = this.categoryRepository.findById(1).get();
		System.out.println(category.getName());
		
		Set<Category> children = category.getChildren();
		children.forEach(child  -> System.out.println(child.getName()));
		assertThat(children.size()).isGreaterThan(0);
	}
	
	
	@Test
	public void testHerarchicalCategories()
	{
			Iterable<Category> findAll = this.categoryRepository.findAll();
			for(Category cate :  findAll)
			{
				if(cate.getParent()==null)
				{
					
					System.out.println(cate.getName());
					for(Category child: cate.getChildren())
					{
						System.out.println("--" + child.getName());
						printChildren(child , 1);
					}
				}
			}
			
	}
	public void printChildren(Category parent , int subLevel)
	{
			
		int newsublevel =  subLevel + 1;
		Set<Category> children =  parent.getChildren();
		
		for(Category child : children)
		{
			for(int i=0 ; i < newsublevel;i++)
			{
				System.out.print("--");
			}
			System.out.println(child.getName());
			printChildren(child, newsublevel);
		}
		
	}
	
	@Test
	public void testCategoriesList()
	{
		List<Category> listRootCategories = this.categoryRepository.listRootCategories(Sort.by("name").ascending());
		listRootCategories.forEach(category -> System.out.println(category.getName()));
	}
	
	@Test
	public void checkUniquness()
	{
			String name  = "computers";
			Category findByName = this.categoryRepository.findByName(name);
			assertThat(findByName).isNotNull();
			
	}
	
	@Test
	public void checkByAliase()
	{
		String name  ="books";
		Category category = this.categoryRepository.findByAlise(name);
		assertThat(category.getAlise()).isEqualTo(name);
		
	}
	
}

