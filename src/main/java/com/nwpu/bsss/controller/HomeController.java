package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.AnnouncementEntity;
import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.dto.ReBlogJsonBody;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.service.AnnounService;
import com.nwpu.bsss.service.BlogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("home")
public class HomeController {
	
	@Resource
	BlogService blogService;
	
	@Resource
	AnnounService announService;
	
	
	@GetMapping("blog/recommend")
	public MyResponseEntity<Object> getRecommendBlog(@RequestHeader("accessToken") String accessToken) {
		Long userId = UserController.token2Id.get(accessToken);
		if (userId == null) {
			return new MyResponseEntity<>(Code.BAD_OPERATION, "token无效", null);
		}
		List<ReBlogJsonBody> blogList = this.blogService.getREblog();
		return new MyResponseEntity<>(Code.OK, "每日推荐博文15条", blogList);
		
	}
	
	@GetMapping("recommend")
	public MyResponseEntity<Object> getAnnouncement(@RequestHeader("accessToken") String accessToken) {
		Long userId = UserController.token2Id.get(accessToken);
		if (userId == null) {
			return new MyResponseEntity<>(Code.BAD_OPERATION, "token无效", null);
		}
		Optional<AnnouncementEntity> anno = this.announService.getFisrtAnnoun();
		
		if (anno.isPresent()) {
			return new MyResponseEntity<>(Code.OK, "今日推荐", anno);
		} else {
			List<AnnouncementEntity> list = new ArrayList<>();
			return new MyResponseEntity<>(Code.BAD_OPERATION, "无公告", list);
		}
	}
}
