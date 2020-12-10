package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.domain.dto.Tag;
import com.nwpu.bsss.response.BloggerInfoResponse;
import com.nwpu.bsss.response.BloggerTagResponse;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/blog")
public class BloggerInfoController {
	
	@Resource
	private UserService userService;
	
	@GetMapping(path = "/blogger")
	public MyResponseEntity<BloggerInfoResponse> getBloggerInfo(@RequestParam("bloggerId") String bloggerId) {
		try {
			long id = Long.parseLong(bloggerId);
			UserInfoEntity userInfoEntity = this.userService.findUserInfoByUserId(id);
			UserEntity userEntity = this.userService.findByUserID(id);

			BloggerInfoResponse bloggerInfoResponse = new BloggerInfoResponse();

			bloggerInfoResponse.setAvatar(userInfoEntity.getAvatarUrl());//Avatar
			bloggerInfoResponse.setClassName(userInfoEntity.getClassName());//class
			bloggerInfoResponse.setLevel(userInfoEntity.getLevel());//level
			bloggerInfoResponse.setNickname(userInfoEntity.getNickName());//nickname

			bloggerInfoResponse.setVerified(userInfoEntity.isVerified());//verified or not
			bloggerInfoResponse.setBlogNum(this.userService.getUserBlogNumByUserId(id));//all blogs num
			bloggerInfoResponse.setFanNum(this.userService.getUserFanNumByUserId(id));//all fans num
			bloggerInfoResponse.setCommentNum(this.userService.getUserCommentNumByUserId(id));//all comment num
			bloggerInfoResponse.setFavoriteNum(this.userService.getFavoriteNumByUserId(id));//all favs num

			long codeAgeTime=new Date().getTime()-userEntity.getTime().getTime();
			bloggerInfoResponse.setCodeAge(codeAgeTime/31536000000L);

			return new MyResponseEntity<>(Code.OK, "ok", bloggerInfoResponse);
		} catch (NumberFormatException e) {
			return new MyResponseEntity<>(Code.BAD_REQUEST, "用户ID格式错误", null);
		} catch (NullPointerException e) {
			return new MyResponseEntity<>(Code.BAD_OPERATION, "用户不存在", null);
		}
		
	}
	
	@GetMapping(path = "/blogger/tags")
	public MyResponseEntity<ArrayList<Tag>> getBloggerTag(@RequestParam("bloggerId") String bloggerId) {
		try {
			long id = Long.parseLong(bloggerId);
			BloggerTagResponse bloggerTagResponse = new BloggerTagResponse();
			return new MyResponseEntity<>(Code.OK, "ok", bloggerTagResponse.getTagList());
		} catch (NumberFormatException e) {
			return new MyResponseEntity<>(Code.BAD_REQUEST, "用户ID格式错误", null);
		} catch (NullPointerException e) {
			return new MyResponseEntity<>(Code.BAD_OPERATION, "用户不存在", null);
		}
	}
}
