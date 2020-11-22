package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.domain.dto.Tag;
import com.nwpu.bsss.response.BloggerInfoResponse;
import com.nwpu.bsss.response.BloggerTagResponse;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.serviceimpl.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
@RestController
@RequestMapping("/blog")
public class BloggerInfoController {
    @GetMapping(path = "/blogger")
    public MyResponseEntity<BloggerInfoResponse> getBloggerInfo(@RequestParam("userId") String userId){
        try{
            Long id = Long.parseLong(userId);
            UserServiceImpl userServiceImpl = new UserServiceImpl();
            UserInfoEntity userInfoEntity = userServiceImpl.findUserInfoByUserId(id);
            BloggerInfoResponse bloggerInfoResponse = new BloggerInfoResponse(userInfoEntity.getClassName());
            return new MyResponseEntity<>(Code.OK,"ok", bloggerInfoResponse);
        } catch(NumberFormatException e){
            return new MyResponseEntity<>(Code.BAD_OPERATION, "用户ID格式错误", null);
        } catch(NullPointerException e){
            return new MyResponseEntity<>(Code.BAD_OPERATION, "用户不存在", null);
        }

    }

    @GetMapping(path = "/blogger/tags")
    public MyResponseEntity<ArrayList<Tag>> getBloggerTag(@RequestParam("userId") String userId){
        try{
            long id = Long.parseLong(userId);
            BloggerTagResponse bloggerTagResponse = new BloggerTagResponse();
            return new MyResponseEntity<>(Code.OK,"ok", bloggerTagResponse.getTagList());
        } catch(NumberFormatException e){
            return new MyResponseEntity<>(Code.BAD_OPERATION, "用户ID格式错误", null);
        } catch(NullPointerException e){
            return new MyResponseEntity<>(Code.BAD_OPERATION, "用户不存在", null);
        }
    }
}
