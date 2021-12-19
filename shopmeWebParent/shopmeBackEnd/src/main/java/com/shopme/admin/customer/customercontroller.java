package com.shopme.admin.customer;

import org.springframework.web.bind.annotation.GetMapping;

public class customercontroller {

	
	@GetMapping("/customers")
	public String createCustomer()
	{
		return "customercreate";
	}
}
