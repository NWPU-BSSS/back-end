package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.exceptions.ValidationException;
import com.nwpu.bsss.response.*;
import com.nwpu.bsss.service.UserService;
import com.nwpu.bsss.utils.UserInfoValidator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;


@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	@PostMapping(path = "/register")
	public MyResponseEntity<RegisterResponse> registerUser(@RequestBody UserEntity user, BindingResult bindingResult) {

		//add time stamp
		user.setTime(new Timestamp(new Date().getTime()));

		//validate email format
		new UserInfoValidator().validate(user, bindingResult);

		//the exception throw here will be taken over by the 'advice' layer
		if (bindingResult.hasErrors()) {
			throw new ValidationException("邮箱格式错误");
		} else {
			try {
				long userId = this.userService.createUser(user); //get userId
				return new MyResponseEntity<>(Code.OK, "ok", new RegisterResponse(userId));
			} catch (DataIntegrityViolationException e) {
				return new MyResponseEntity<>(Code.BAD_OPERATION, "用户已存在", null);
			}
		}
	}

	@GetMapping(path = "/info")
	public MyResponseEntity<UserInfoResponse> getUserInfo(@RequestParam("userId") String userId) {
		try {
			long id = Long.parseLong(userId);
			UserInfoResponse userInfoResponse = new UserInfoResponse();
			userInfoResponse.setEmail(this.userService.findByUserID(id).getEmail());

			return new MyResponseEntity<>(Code.OK, "ok", userInfoResponse);
		} catch (NumberFormatException e) {
			return new MyResponseEntity<>(Code.BAD_OPERATION, "用户ID格式错误", null);
		} catch (NullPointerException e) {
			return new MyResponseEntity<>(Code.BAD_OPERATION, "用户不存在", null);
		}
	}
	@GetMapping(path = "/blog/blogger")
	public MyResponseEntity<BloggerInfoResponse> getBloggerInfo(@RequestParam("userId") String userId){
		try{
			long id = Long.parseLong(userId);
			BloggerInfoResponse bloggerInfoResponse = new BloggerInfoResponse(id);
			return new MyResponseEntity<>(Code.OK,"ok", bloggerInfoResponse);
		} catch(NumberFormatException e){
			return new MyResponseEntity<>(Code.BAD_OPERATION, "用户ID格式错误", null);
		} catch(NullPointerException e){
			return new MyResponseEntity<>(Code.BAD_OPERATION, "用户不存在", null);
		}

	}

	@GetMapping(path = "/blog/blogger/tags")
	public MyResponseEntity<ArrayList<String>> getBloggerTag(@RequestParam("userId") String userId){
		try{
			long id = Long.parseLong(userId);
			BloggerTagResponse bloggerTagResponse = new BloggerTagResponse(id);
			return new MyResponseEntity<>(Code.OK,"ok", bloggerTagResponse.getTagList());
		} catch(NumberFormatException e){
			return new MyResponseEntity<>(Code.BAD_OPERATION, "用户ID格式错误", null);
		} catch(NullPointerException e){
			return new MyResponseEntity<>(Code.BAD_OPERATION, "用户不存在", null);
		}
	}

}
