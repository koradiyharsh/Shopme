package com.shopme.admin.users;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageRequestDto;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false) 
public class userRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager EntityManager;
	
	@Test
	public void testCreateOneRoleUser()
	{
			Role roleAdmin = EntityManager.find(Role.class, 1);
			User user = new User("harsh@gmail.com", "harsh11199", "harsh", "Koradiya");
			user.addRole(roleAdmin);
			User savedUser = userRepository.save(user);
			assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateTwoRoleUser()
	{
		
		User user2 = new User("koradiya@gmail.com", "Ph*11199", "pooja", "jain");
		Role role = new Role(2);
		Role role2 = new Role(3);
		user2.addRole(role);
		user2.addRole(role2);
		
		User savedMultiRole = userRepository.save(user2);
		assertThat(savedMultiRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers()
	{
		Iterable<User> listusers = userRepository.findAll();
		listusers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById()
	{
			Optional<User> findById = userRepository.findById(1);
			System.out.println(findById);
			assertThat(findById).isNotNull();
	}
	
	@Test
	public void UpdateUserDetail()
	{
		User finduser = userRepository.findById(1).get();
		finduser.setEnabled(true);
		finduser.setEmail("202012095@gmail.com");
		userRepository.save(finduser);
	}
	
	@Test
	public void testUpdateUserRole()
	{
	
		User userpooja = userRepository.findById(2).get();
		
		Role roleshipper = new Role(4);
		Role roleEditor =  new Role(3);
		
		userpooja.getRoles().remove(roleEditor);
		userpooja.addRole(roleshipper);
		userRepository.save(userpooja);
	}
	
	
	@Test
	public void checkUserByEmail()
	{
			String email = "koradiya@gmail.com";
			User userByEmail = userRepository.getUserByEmail(email);
			assertThat(userByEmail).isNotNull();
			
	}
	
	@Test
	public void countByID()
	{
		Integer id =  1;
		Integer countByid = this.userRepository.countByid(id);
		assertThat(countByid).isNotNull().isGreaterThan(0);

	}
	
	@Test
	public void UpdateEnableStatus()
	{
		Integer id = 1;
		this.userRepository.updateEnablestatus(id, false);
	}
	
	@Test
	public void UpdateDisbaleStatus()
	{
		Integer id  =  1;
		this.userRepository.updateEnablestatus(id , true);
	}
	
	@Test
	public void getpageList()
	{
		int pageNo = 1;
		int pagesize  = 5;
		Pageable page  = PageRequest.of(pageNo, pagesize);
		Page<User> getpagelist = this.userRepository.findAll(page);
		List<User> listusers = getpagelist.getContent();
		getpagelist.forEach( listuser -> System.out.println(listuser));
		
		assertThat(listusers.size()).isEqualTo(pagesize);
		
		
	}
	
	@Test
	public void Testkeyword()
	{
		int pageNo =  0;
		int pagesize  = 4;
		Pageable page  =  PageRequest.of(pageNo, pagesize);
		String Keyword = "patel";
		Page<User> findAll = this.userRepository.findAll(Keyword, page);
		
		List<User> listUser = findAll.getContent();
		listUser.forEach(user -> System.out.println(user));
		assertThat(listUser.size()).isGreaterThan(0);
		
		
	}
	

}
