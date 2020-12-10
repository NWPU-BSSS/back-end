package com.nwpu.bsss.advice;

import com.nwpu.bsss.controller.BlogController;
import com.nwpu.bsss.exceptions.FavoriteStatusException;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created: 2020-12-10 16:43:06<b>
 *
 * @author Zejia Lin
 */
@ControllerAdvice(basePackageClasses = {BlogController.class})
@ResponseBody
public class BlogControllerAdvice {
	
	@ExceptionHandler(FavoriteStatusException.class)
	public MyResponseEntity<Object> handleFavoriteStatusException(FavoriteStatusException e) {
		return new MyResponseEntity<>(Code.BAD_OPERATION, e.getMessage(), null);
	}
}
