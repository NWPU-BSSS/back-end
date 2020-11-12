package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.exceptions.ValidationException;
import com.nwpu.bsss.repository.UserRepository;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.response.RegisterResponse;
import com.nwpu.bsss.service.UserService;
import com.nwpu.bsss.utils.UserInfoValidator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserRepository userRepository;
	
	@Resource
	private UserService userService;
	
	@RequestMapping("/allUser")
	public MyResponseEntity<List<UserEntity>> getAllUsers() {
		return new MyResponseEntity<>(Code.OK, "ok", this.userRepository.findAll());
	}
	
	@PostMapping(path = "/register")
	public MyResponseEntity<RegisterResponse> registerUser(@RequestBody UserEntity user, BindingResult bindingResult) {
		
		//add time stamp
		user.setTime(new Timestamp(new Date().getTime()));
		
		//validate email format
		new UserInfoValidator().validate(user, bindingResult);
		
		if (bindingResult.hasErrors()) {
			throw new ValidationException("Validation errors occurred");
		} else {
			long userId = this.userService.createUser(user); //get userId
			return new MyResponseEntity<>(Code.OK, "ok", new RegisterResponse(userId));
		}
	}
	
	@GetMapping(path = "/info")
	public MyResponseEntity<UserEntity> getUserInfo(@RequestParam("id") int id) {
		return new MyResponseEntity<>(Code.OK, "ok", this.userService.findByUserID(id));
	}
	
}
