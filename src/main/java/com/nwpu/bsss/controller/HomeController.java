package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.AnnouncementsEntity;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.service.AnnounService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
@RequestMapping("home")
public class HomeController {

	@Resource
	AnnounService announService;
	
	@GetMapping("announcement")
	public MyResponseEntity<Object> getAnnouncement() {
		Optional<AnnouncementsEntity> anno = this.announService.getFisrtAnnoun();
		
		if (anno.isPresent()) {
			return new MyResponseEntity<>(Code.OK, "今日公告", anno);
		} else {
			AnnouncementsEntity emptyAnn = new AnnouncementsEntity();
			emptyAnn.setId(-1);
			return new MyResponseEntity<>(Code.OK, "ok", emptyAnn);
		}
	}
}
