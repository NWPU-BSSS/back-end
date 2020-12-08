package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.AnnouncementsEntity;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.service.AnnounService;
import com.nwpu.bsss.service.BlogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
@RequestMapping("home")
public class HomeController {
	
	@Resource
	BlogService blogService;
	
	@Resource
	AnnounService announService;
	
	@GetMapping("recommend")
	public MyResponseEntity<Object> getAnnouncement(@RequestHeader("accessToken") String accessToken) {
		Long userId = UserController.token2Id.get(accessToken);
		if (userId == null) {
			return new MyResponseEntity<>(Code.BAD_OPERATION, "token无效", null);
		}
		Optional<AnnouncementsEntity> anno = this.announService.getFisrtAnnoun();
		
		if (anno.isPresent()) {
			return new MyResponseEntity<>(Code.OK, "今日推荐", anno);
		} else {
			AnnouncementsEntity emptyAnn = new AnnouncementsEntity();
			emptyAnn.setId(-1);
			return new MyResponseEntity<>(Code.OK, "ok", emptyAnn);
		}
	}
}
