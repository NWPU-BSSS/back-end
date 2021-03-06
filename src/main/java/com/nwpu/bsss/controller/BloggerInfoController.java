package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.domain.dto.Tag;
import com.nwpu.bsss.response.BloggerInfoResponse;
import com.nwpu.bsss.response.BloggerTagResponse;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.service.LikeService;
import com.nwpu.bsss.service.BlogService;
import com.nwpu.bsss.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BloggerInfoController {
	
	@Resource
	private UserService userService;

	@Resource
	private BlogService blogService;

	@Value("${serverURL}")
	private String serverURL;

	@GetMapping(path = "/blogger")
	public MyResponseEntity<BloggerInfoResponse> getBloggerInfo(@RequestParam("bloggerId") String bloggerId) {
		try {
			long id = Long.parseLong(bloggerId);
			UserInfoEntity userInfoEntity = this.userService.findUserInfoByUserId(id);
			UserEntity userEntity = this.userService.findByUserID(id);

			BloggerInfoResponse bloggerInfoResponse = new BloggerInfoResponse();

			bloggerInfoResponse.setAvatar(serverURL+userInfoEntity.getAvatarUrl());//Avatar
			bloggerInfoResponse.setClassName(userInfoEntity.getClassName());//class
			bloggerInfoResponse.setLevel(userInfoEntity.getLevel());//level
			bloggerInfoResponse.setNickname(userInfoEntity.getNickName());//nickname

			bloggerInfoResponse.setVerified(userInfoEntity.isVerified());//verified or not
			bloggerInfoResponse.setBlogNum(this.userService.getUserBlogNumByUserId(id));//all blogs num
			bloggerInfoResponse.setFanNum(this.userService.getUserFanNumByUserId(id));//all fans num
			bloggerInfoResponse.setCommentNum(this.userService.getUserCommentNumByUserId(id));//all comment num
			bloggerInfoResponse.setFavoriteNum(this.userService.getFavoriteNumByUserId(id));//all favs num
			bloggerInfoResponse.setLikeNum(this.userService.getLikeNumByUserId(id));//all like num

			long codeAgeTime=new Date().getTime()-userEntity.getTime().getTime();
			bloggerInfoResponse.setCodeAge(codeAgeTime/31536000000L);

			return new MyResponseEntity<>(Code.OK, "ok", bloggerInfoResponse);
		} catch (NumberFormatException e) {
			return new MyResponseEntity<>(Code.BAD_REQUEST, "Invalid userId", null);
		} catch (NullPointerException e) {
			return new MyResponseEntity<>(Code.BAD_OPERATION, "User not exist", null);
		}
		
	}
	
	@GetMapping(path = "/blogger/tags")
	public MyResponseEntity<List<Tag>> getBloggerTag(@RequestParam("bloggerId") String bloggerId) {
		try {
			if(StringUtils.isBlank(bloggerId)){
				return new MyResponseEntity<>(Code.BAD_REQUEST,"Invalid param",null);
			}

			long id = Long.parseLong(bloggerId);
			if(userService.findByUserID(id) == null){
				return new MyResponseEntity<>(Code.BAD_OPERATION,"Blogger not exist",null);
			}
			BloggerTagResponse bloggerTagResponse = new BloggerTagResponse();
			bloggerTagResponse = blogService.getTags(id);
			List<Tag> tagList = new ArrayList<>(bloggerTagResponse.getTagList().values());
			return new MyResponseEntity<>(Code.OK, "ok", tagList);
		} catch (NumberFormatException e) {
			return new MyResponseEntity<>(Code.BAD_REQUEST, "Invalid userId", null);
		} catch (NullPointerException e) {
			return new MyResponseEntity<>(Code.BAD_OPERATION, "User not exist", null);
		}
	}
}
