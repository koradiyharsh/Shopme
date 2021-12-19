package com.shopme.admin.brand.customers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class cutomerAppointment 
{

	@GetMapping("/customers")
	public String getAppointmentDetails()
	{
			return "getAppointment";
	}
	
}
