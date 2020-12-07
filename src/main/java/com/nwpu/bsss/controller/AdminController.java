package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.response.UserListElement;
import com.nwpu.bsss.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Project: bsss<br>
 * Created: 11:43 AM, 12/7/2020<br>
 *
 * @author Zejia Lin
 * @version 0.0.1
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Resource
	private UserService userService;
	
	@GetMapping("/users")
	public MyResponseEntity<List<UserListElement>> getUserList() {
		List<UserInfoEntity> entities = this.userService.findAll();
		List<UserListElement> list = entities.stream()
				.map(e -> new UserListElement(
								e.getId(),
								e.getNickName(),// TODO: 12/7/2020 username
								e.getNickName(),
								e.getAvatarUrl(),
								e.getIntroduction(),
								e.getGender()
						)
				)
				.collect(Collectors.toList());
		return new MyResponseEntity<>(Code.OK, "ok", list);
	}
	
}
