package com.nwpu.bsss.advice;

import com.nwpu.bsss.controller.UserController;
import com.nwpu.bsss.exceptions.ValidationException;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.response.RegisterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = {UserController.class})
@ResponseBody
public class UserControllerAdvice {
	
	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public MyResponseEntity<RegisterResponse> handleValidationException(ValidationException e) {
		return new MyResponseEntity<>(Code.BAD_REQUEST, e.getMessage(), null);
	}
	
}
