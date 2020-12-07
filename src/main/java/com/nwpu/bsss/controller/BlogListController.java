package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.dto.ReBlogJsonBody;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.service.BlogListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
	public MyResponseEntity<Object> getRecommendBlog(@RequestHeader("accessToken") String accessToken) {
		Long userId = UserController.token2Id.get(accessToken);
		if (userId == null) {
			return new MyResponseEntity<>(Code.BAD_OPERATION, "token无效", null);
		}
		List<ReBlogJsonBody> blogList = this.blogListService.getREblog();
		return new MyResponseEntity<>(Code.OK, "每日推荐博文15条", blogList);
	}
	
}