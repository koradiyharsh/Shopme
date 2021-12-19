package com.shopme.admin.users;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entities.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoriesTest {
		
	
	@Autowired
	RoleRepositories userRepositories;
	
	
	@Test
	public void testCreateFirstRole()
	{
		Role role = new Role("Admin", "Manage EveryThing");
		Role savedRole = userRepositories.save(role); 
		
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRoles()
	{
		Role roleSalaesPerson = new Role("Salesperson", "manage product price , "+"customer  , shipping , orders and sales report");
		
		Role roleEditor = new Role("Editor", "manage categories , brands , "+"products , articles and menus");
		
		Role roleShipper = new Role("shipper", "view Products  , view Orders "+ "and update order status ");
		
		Role RoleAssistant = new Role("Assistant", "manage question and reviews");
		
		
		userRepositories.saveAll(List.of(roleSalaesPerson, roleEditor, roleShipper, RoleAssistant));
		
		
		
		
		
		
	}

}
