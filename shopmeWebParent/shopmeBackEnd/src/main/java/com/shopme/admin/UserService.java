package com.shopme.admin;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.admin.users.RoleRepositories;
import com.shopme.admin.users.UserRepository;
import com.shopme.common.entities.Role;
import com.shopme.common.entities.User;

@Service
@Transactional
public class UserService 
{
		
	@Autowired
	private UserRepository userRepository;
	
	public static int PAGE_PER_USER = 4;
	
	@Autowired
	private RoleRepositories roleRepositories;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> getAllUsers()
	{
		return 	(List<User>) this.userRepository.findAll(Sort.by("firstName").ascending());
	}
	
	public User getByemail(String email)
	{
		User userByEmail = this.userRepository.getUserByEmail(email);
		return userByEmail;
	}
	
	public List<Role> getAllRole()
	{
			return (List<Role>) this.roleRepositories.findAll();
	}
	
	
	
	public User saveUser(User user)
	{
		boolean isUpdateingUser =  (user.getId()!=null);
		
		if(isUpdateingUser) {
			
			
			User ExistingUser  =  this.userRepository.findById(user.getId()).get();
			if(user.getPassword().isEmpty()) {
				user.setPassword(ExistingUser.getPassword());
			}
			else {
				encodePassword(user);
				
			}
		}
		else {
			encodePassword(user);
		}
		
		return this.userRepository.save(user);
	}
	
	public User updateAccountDetails(User userInForm )
	{
		
		User user = this.userRepository.findById(userInForm.getId()).get();
		if(!userInForm.getPassword().isEmpty())
		{
			
			user.setPassword(userInForm.getPassword());
			encodePassword(user);
		}
		if(userInForm.getPhoto() != null)
		{
			
			user.setPhoto(userInForm.getPhoto());
			
		}
		
		
		user.setFirstName(userInForm.getFirstName());
		user.setLastName(userInForm.getLastName());
		this.userRepository.save(user);
		
		return user;
		
	}
	
	private void encodePassword(User user)
	{
		String encodedpassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedpassword);
	}
	
	public boolean isEmailUnique(Integer id , String email)
	{
		User userByEmail = this.userRepository.getUserByEmail(email);
		
		if(userByEmail==null) {
			return true;
		}
		
		boolean isCreatingnew  = (id==null);
		if(isCreatingnew) {
			if(userByEmail!=null) return false;
		}
		else {
			
			if(userByEmail.getId()!=id) {
				return false;
			}
		}
		
		return true;
	}


	public User get(Integer id) throws UserNotFoundExcepion {
		
		try {
			return this.userRepository.findById(id).get();
		} catch (NoSuchElementException e) {
				throw new UserNotFoundExcepion("could not find Any User with ID " + id);
		}
	}
	
	public void DeleteUser(Integer id) throws UserNotFoundExcepion
	{
		Integer countByid = this.userRepository.countByid(id);
		if(countByid==null  || countByid == 0) {
			throw new UserNotFoundExcepion("could not find Any User with ID " + id);
		}
		
		this.userRepository.deleteById(id);
	}
	
	public void UpdateEnabledStatus(Integer id , boolean result) 
	{
		this.userRepository.updateEnablestatus(id, result);
	}
	
	public Page<User> listAllPage(int pageNumber , String sortFiled, String sortDir , String Keyword)
	{
	
	Sort sort = Sort.by(sortFiled);
	sort =  sortDir.equals("asc") ? sort.ascending() : sort.descending();
	Pageable page =  PageRequest.of(pageNumber-1, PAGE_PER_USER , sort);
	Page<User> getpagelist = this.userRepository.findAll(page);
	if(Keyword!=null) {
		return this.userRepository.findAll(Keyword ,  page);
	}
	return getpagelist;
	
	
	
	
	
		
		
		
	}
	
	
	
	
}
