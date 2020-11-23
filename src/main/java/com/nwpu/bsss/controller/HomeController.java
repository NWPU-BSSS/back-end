package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.AnnouncementEntity;
import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.service.AnnounService;
import com.nwpu.bsss.service.BlogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RequestMapping("home")
public class HomeController {

    @Resource
    BlogService blogService;

    @Resource
    AnnounService announService;

    @GetMapping("blog/recommend")
    public MyResponseEntity<Object> getRecommendBlog(@RequestParam String accessToken){
        // TODO: 2020/11/23 验证accessToken是否有效
        List<BlogEntity> blogList = blogService.getREblog();
        return new MyResponseEntity<>(Code.OK,"每日推荐博文15条",blogList);

    }

    @GetMapping("recommend")
    public MyResponseEntity<Object> getAnnouncement(@RequestParam String accessToken){
        // TODO: 2020/11/23 验证accessToken是否有效
        Optional<AnnouncementEntity> anno = announService.getFisrtAnnoun();
        if(anno.isPresent()){
            return new MyResponseEntity<>(Code.OK,"今日推荐",anno);
        }
        else{
            return new MyResponseEntity<>(Code.BAD_OPERATION,"无公告",null);
        }
    }
}
