package com.nwpu.bsss.advice;

import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public MyResponseEntity<Object> handleGlobalExceptions(Exception e) {
		return new MyResponseEntity<>(Code.BAD_OPERATION, e.getMessage(), null);
	}
	
}
