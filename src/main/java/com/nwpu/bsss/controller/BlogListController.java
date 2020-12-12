package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.dto.FavBlogJsonBody;
import com.nwpu.bsss.domain.dto.KeywordBlogJsonBody;
import com.nwpu.bsss.domain.dto.ReBlogJsonBody;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.service.BlogListService;
import com.nwpu.bsss.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created: 2020-12-07 20:44:19<br>
 *
 * @author Zejia Lin
 */
@RestController
public class BlogListController {
	
	@Resource
	BlogListService blogListService;

	@Resource
	UserService userService;

	@GetMapping("/blog/list/recommend")
	public MyResponseEntity<Object> getRecommendBlog(@RequestParam("page") String page) {
		int p;
		try {
			p = Integer.parseInt(page);
			if (p<0) throw new ArrayIndexOutOfBoundsException();
		}catch (Exception e){
			return new MyResponseEntity<>(Code.BAD_REQUEST,"Invalid page",null);
		}
		List<ReBlogJsonBody> blogList = this.blogListService.getRecomBlog(p);
		return new MyResponseEntity<>(Code.OK, "ok", blogList);
	}

	@GetMapping("/search")
	public MyResponseEntity getKeyWordBlog(@RequestParam("word") String word){
		List<KeywordBlogJsonBody> blogList= this.blogListService.getKeywordBlog(word);
		return new MyResponseEntity(Code.OK,"ok",blogList);
	}

	@GetMapping("/blog/list/favs")
	public MyResponseEntity<Object> getFavoriteBlogs(@RequestParam("userId") String userId) {

		try {
			long user_id = Long.parseLong(userId);
			List<FavBlogJsonBody> favBlogList = blogListService.getFavsBlog(user_id);
			return new MyResponseEntity<>(Code.OK, "ok", favBlogList);
		}catch (NumberFormatException e){
			return new MyResponseEntity<>(Code.BAD_REQUEST,"Invalid userId",null);
		}

	}

	@GetMapping("/blog/list/followed")
	public MyResponseEntity<Object> getFollowedBloggersBlogs(@RequestParam("page") String page,
															 @RequestParam("userId") String userId){
		if (StringUtils.isBlank(userId))
			return new MyResponseEntity<>(Code.BAD_REQUEST, "Invalid userId", null);
		if (StringUtils.isBlank(page))
			return new MyResponseEntity<>(Code.BAD_REQUEST, "Invalid page", null);
		try {
			long uId = Long.parseLong(userId);
			int pageNum = Integer.parseInt(page);
			if (pageNum < 0)
				return new MyResponseEntity<>(Code.BAD_OPERATION, "Invalid page", null);
			if (userService.findByUserID(uId) == null)
				return new MyResponseEntity<>(Code.BAD_OPERATION, "User not exist", null);
			List<KeywordBlogJsonBody> blogList = blogListService.getFollowedBlog(uId);
			return new MyResponseEntity<>(Code.OK, "ok", blogList);
		}catch (NumberFormatException e){
			return new MyResponseEntity<>(Code.BAD_REQUEST, "Invalid param", null);
		}
	}

	@GetMapping("/blog/list/user")
	public MyResponseEntity<Object> getUserBlogs(@RequestParam("page") String page,
												 @RequestParam("userId") String userId){
		int pageSize = 15;
		if (StringUtils.isBlank(userId))
			return new MyResponseEntity<>(Code.BAD_REQUEST, "Invalid userId", null);
		if (StringUtils.isBlank(page))
			return new MyResponseEntity<>(Code.BAD_REQUEST, "Invalid page", null);
		try {
			long uId = Long.parseLong(userId);
			int pageNum = Integer.parseInt(page);
			if (pageNum < 0)
				return new MyResponseEntity<>(Code.BAD_REQUEST, "Invalid page", null);
			if (userService.findByUserID(uId) == null)
				return new MyResponseEntity<>(Code.BAD_OPERATION, "User not exist", null);
			List<KeywordBlogJsonBody> blogList = blogListService.getBlogsPageByUserId(uId, pageNum, pageSize);
			return new MyResponseEntity<>(Code.OK, "ok", blogList);
		}catch (NumberFormatException e){
			return new MyResponseEntity<>(Code.BAD_REQUEST, "Invalid param", null);
		}
	}

	@GetMapping("/blog/list/recent")
	public MyResponseEntity<Object> getRecentBlogs(@RequestParam("page") String page){
		Long pageNum;
		try{
			pageNum=Long.valueOf(page);
		}catch (NumberFormatException numberFormatException){
			return new MyResponseEntity(Code.BAD_REQUEST,"Invalid page",null);
		}
		List<KeywordBlogJsonBody> blogList=this.blogListService.getRecentBlog(pageNum);
		return new MyResponseEntity(Code.OK,"ok",blogList);
	}
}
