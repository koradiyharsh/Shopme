package com.shopme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.shopme.admin.users.CategoryRepository;
import com.shopme.admin.users.category.CategoryService;
import com.shopme.common.entities.Category;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTest 
{
	
	@MockBean
	private CategoryRepository categoryRepository;
	
	@InjectMocks
	private CategoryService service;
	
	
	
	@Test
	public void testCheckUniqueInNewModeReturnDuplicateName()
	{
			Integer id  = null;
			String name  = "computers";
			String alise  = "abc";
			
			Category category  = new Category(id, name, alise);
			
			Mockito.when(categoryRepository.findByName(name)).thenReturn(category);
			Mockito.when(categoryRepository.findByAlise(alise)).thenReturn(null);
			String result = this.service.checkUnique(id, name, alise);
			
			assertThat(result).isEqualTo("DuplicateName");
			
			
	}
	
	@Test
	public void testCheckUniqueInNewModeReturnDuplicateAlise()
	{
			Integer id  = null;
			String name  = "xyz";
			String alise  = "hard_drive";
			
			Category category  = new Category(id, name, alise);
			
			Mockito.when(categoryRepository.findByName(name)).thenReturn(null);
			Mockito.when(categoryRepository.findByAlise(alise)).thenReturn(category);
			String result = this.service.checkUnique(id, name, alise);
			
			
			assertThat(result).isEqualTo("DuplicateAlise");
			
	}
	@Test
	public void testCheckUniqueInNewModeReturnOK()
	{
			Integer id  = null;
			String name  = "XYZ";
			String alise  = "ABC";
			
			Category category  = new Category(id, name, alise);
			
			Mockito.when(categoryRepository.findByName(name)).thenReturn(null);
			Mockito.when(categoryRepository.findByAlise(alise)).thenReturn(null);
			String result = this.service.checkUnique(id, name, alise);
			
			
			assertThat(result).isEqualTo("OK");
	
	}
	@Test
	public void testCheckUniqueInEditModeReturnDuplicateName()
	{
			Integer id  = 2;
			String name  = "computers";
			String alise  = "abc";
			
			Category category  = new Category(1, name, alise);
			
			Mockito.when(categoryRepository.findByName(name)).thenReturn(category);
			Mockito.when(categoryRepository.findByAlise(alise)).thenReturn(null);
			String result = this.service.checkUnique(id, name, alise);
			
			assertThat(result).isEqualTo("DuplicateNameWithID");
			
			
	}
	@Test
	public void testCheckUniqueInEditModeReturnDuplicateAlise()
	{
			Integer id  = 2;
			String name  = "xyz";
			String alise  = "hard_drive";
			
			Category category  = new Category(1, name, alise);
			
			Mockito.when(categoryRepository.findByName(name)).thenReturn(null);
			Mockito.when(categoryRepository.findByAlise(alise)).thenReturn(category);
			String result = this.service.checkUnique(id, name, alise);
			
			assertThat(result).isEqualTo("DuplicateAliseWithID");
			
			
	}
	@Test
	public void testCheckUniqueInEditModeReturnOK()
	{
			Integer id  = 2;
			String name  = "computers";
			String alise  = "computers";
			
			Category category  = new Category(2, name, alise);
			
			Mockito.when(categoryRepository.findByName(name)).thenReturn(category);
			Mockito.when(categoryRepository.findByAlise(alise)).thenReturn(category);
			String result = this.service.checkUnique(id, name, alise);
			
			
			assertThat(result).isEqualTo("OK");
	
	}
	
}
