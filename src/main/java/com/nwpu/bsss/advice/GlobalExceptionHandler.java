package com.nwpu.bsss.advice;

import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	public MyResponseEntity<Object> handleGlobalExceptions(Exception e) {
		String msg = e.getMessage();
		if(msg == null) {
			msg = e.getLocalizedMessage();
		}
		log.error(msg);
		return new MyResponseEntity<>(Code.BAD_OPERATION, msg, null);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.OK)
	public MyResponseEntity<Object> handleMissingParamExceptions(MissingServletRequestParameterException e) {
		log.error(e.getMessage());
		return new MyResponseEntity<>(Code.BAD_REQUEST, "缺少参数", null);
	}

	@ExceptionHandler(MissingRequestHeaderException.class)
	@ResponseStatus(HttpStatus.OK)
	public MyResponseEntity<Object> handleMissingParamExceptions(MissingRequestHeaderException e) {
		log.error(e.getMessage());
		return new MyResponseEntity<>(Code.BAD_REQUEST, "缺少请求头参数", null);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.OK)
	public MyResponseEntity<Object> handleMisParamTypeExceptions(MethodArgumentTypeMismatchException e) {
		log.error(e.getMessage());
		return new MyResponseEntity<>(Code.BAD_REQUEST, "参数类型错误", null);
	}
}
