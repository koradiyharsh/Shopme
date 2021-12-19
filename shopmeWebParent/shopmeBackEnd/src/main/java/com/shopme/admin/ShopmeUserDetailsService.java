package com.shopme.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shopme.admin.security.shopmeUserDetails;
import com.shopme.admin.users.UserRepository;
import com.shopme.common.entities.User;

public class ShopmeUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userRepository.getUserByEmail(email);
		if(user!=null)
		{
			return new shopmeUserDetails(user);
		}
		throw  new UsernameNotFoundException("could not found user with this Email "+email);
	}

}
