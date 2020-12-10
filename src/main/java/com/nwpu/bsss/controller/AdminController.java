package com.nwpu.bsss.controller;


import com.nwpu.bsss.domain.AnnouncementsEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.domain.dto.AdminValidationBody;
import com.nwpu.bsss.domain.dto.DeleteBlogBody;
import com.nwpu.bsss.domain.dto.DeleteUserBody;
import com.nwpu.bsss.domain.dto.PostAnnouncementBody;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.response.UserListElement;
import com.nwpu.bsss.service.AdminService;
import com.nwpu.bsss.service.UserService;
import com.nwpu.bsss.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author alecHe
 * @desc 管理员接口
 * @date 2020-12-07 10:28:25
 */
@Slf4j
@RequestMapping("/admin")
@RestController
public class AdminController {
	
	@Resource
	AdminService adminService;
	@Resource
	UserService userService;
	
	private static final String InternalError = "内部错误";
	
	@GetMapping("/users")
	public MyResponseEntity<List<UserListElement>> getUserList(@RequestBody AdminValidationBody validation) {
		
		if (this.adminService.check(validation.getAdmin(), validation.getPassword()) == -1) {
			log.error("管理员密码错误");
			return new MyResponseEntity<>(Code.BAD_OPERATION, "管理员账号或密码错误", null);
		}
		
		List<UserInfoEntity> entities = this.adminService.findAllUsers();
		List<UserListElement> list = entities.stream()
				.map(e -> new UserListElement(
								e.getId(),
								this.userService.findByUserID(e.getId()).getUserName(),
								e.getNickName(),
								e.getAvatarUrl(),
								e.getIntroduction(),
								e.getGender()
						)
				)
				.collect(Collectors.toList());
		return new MyResponseEntity<>(Code.OK, "ok", list);
	}
	
	@PostMapping("announcement")
	public MyResponseEntity<Object> makeAnnouncement(@RequestBody PostAnnouncementBody annBody) {
		
		long publisher = this.adminService.check(annBody.getAdmin(), annBody.getPassword());
		Timestamp start;
		Timestamp end;
		long annId;
		
		if (publisher == -1) {
			log.error("管理员密码错误");
			return new MyResponseEntity<>(Code.BAD_OPERATION, "管理员账号或密码错误", null);
		}
		try {
			start = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(annBody.getStartTime()).getTime());
			end = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(annBody.getEndTime()).getTime());
			
			if (start.after(end) || StringUtils.isBlank(annBody.getContent())) {
				throw new ParseException("", 0);
			}
			else if(StringUtils.isBlank(annBody.getTitle())){
				annBody.setTitle("无标题");
			}
			
			annId = this.adminService.makeAnnounce(new AnnouncementsEntity(annBody.getTitle(), publisher, start, end,
					Tools.timestamp(), annBody.getContent()));
			
		} catch (ParseException e) {
			log.error("时间格式或内容缺失错误");
			return new MyResponseEntity<>(Code.BAD_OPERATION, "时间格式或内容缺失错误", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new MyResponseEntity<>(Code.BAD_OPERATION, InternalError, null);
		}
		
		return MyResponseEntity.sendOK(null);
		
	}
	
	@DeleteMapping("blog")
	public MyResponseEntity<Object> deleteBlog(@RequestBody DeleteBlogBody blogBody) {
		long admin_ = this.adminService.check(blogBody.getAdmin(), blogBody.getPassword());
		if (admin_ == -1) {
			log.error("管理员密码错误");
			return new MyResponseEntity<>(Code.BAD_OPERATION,
					"Invalid Admin username or password", null);
		}
		long blogId;
		try {
			blogId = Long.parseLong(blogBody.getBlogId());
		}catch (Exception e){
			log.error(blogBody.getBlogId());
			return new MyResponseEntity<>(Code.BAD_REQUEST, "Invalid blogId", null);
		}

		if (this.adminService.deleteBlog(blogId)) {
			return MyResponseEntity.sendOK(null);
		} else {
			return new MyResponseEntity<>(Code.BAD_OPERATION, "Blog not exist", null);
		}
	}
	
	@DeleteMapping("user")
	public MyResponseEntity<Object> deleteUser(@RequestBody DeleteUserBody userBody) {

		long admin_ = this.adminService.check(userBody.getAdmin(), userBody.getPassword());
		if (admin_ == -1) {
			log.error("管理员密码错误");
			return new MyResponseEntity<>(Code.BAD_OPERATION,
					"Invalid Admin username or password", null);
		}
		long userId;
		try {
			userId = Long.parseLong(userBody.getUserId());
		}catch (Exception e){
			log.error(userBody.getUserId());
			return new MyResponseEntity<>(Code.BAD_REQUEST, "Invalid userId", null);
		}

		if (this.adminService.deleteUser(userId)) {
			return MyResponseEntity.sendOK(null);
		} else {
			return new MyResponseEntity<>(Code.BAD_OPERATION, "User not exist", null);
		}
	}
	
}
