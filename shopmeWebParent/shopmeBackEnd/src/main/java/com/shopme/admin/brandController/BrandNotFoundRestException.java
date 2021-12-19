package com.shopme.admin.brandController;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND , reason = "Brand not Found")
public class BrandNotFoundRestException extends Exception {

}
