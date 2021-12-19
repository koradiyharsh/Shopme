package com.shopme.admin.users;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEnocodeTest 
{
	@Test
	public void passwordEncode()
	{
		BCryptPasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();
		String rawpassword = "Harsh*11199";
		String encode = passwordEncoder.encode(rawpassword);
		
		boolean matches = passwordEncoder.matches(rawpassword, encode);
		assertThat(matches).isTrue();
		System.out.println(encode);
	}
}
