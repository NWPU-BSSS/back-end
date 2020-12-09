package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.dto.FavBlogJsonBody;
import com.nwpu.bsss.domain.dto.KeywordBlogJsonBody;
import com.nwpu.bsss.domain.dto.ReBlogJsonBody;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.service.BlogListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
	
	
	@GetMapping("/home/blog/recommend")
	public MyResponseEntity<Object> getRecommendBlog(@RequestHeader("accessToken") String accessToken,
													 @RequestParam("userId") String userId) {
		List<ReBlogJsonBody> blogList = this.blogListService.getREblog();
		return new MyResponseEntity<>(Code.OK, "每日推荐博文15条", blogList);
	}

	@GetMapping("/search")
	public MyResponseEntity getKeyWordBlog(@RequestParam("word") String word){
		List<KeywordBlogJsonBody> blogList= this.blogListService.getKeywordBlog(word);
		if(blogList.size()==0){
			return new MyResponseEntity(Code.BAD_OPERATION,"查询无结果",null);
		}
		return new MyResponseEntity(Code.OK,"ok",blogList);
	}

	@GetMapping("/blog/list/favs")
	public MyResponseEntity<Object> getFavoriteBlogs(@RequestParam("userId") String userId) {

		try {
			long user_id = Long.parseLong(userId);
			List<FavBlogJsonBody> favBlogList = blogListService.getFavsBlog(user_id);
			return new MyResponseEntity<>(Code.OK, "ok", favBlogList);
		}catch (NumberFormatException e){
			return new MyResponseEntity<>(Code.BAD_REQUEST,"userId格式错误",null);
		}

	}
}
