package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.response.*;
import com.nwpu.bsss.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author JerryChan
 * @date 2020-12-08 16:55:09
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/baseInfo")
    public MyResponseEntity<UserBaseInfoResponse> getUserBaseInfo(@RequestHeader("accessToken") String accessToken,
                                                                  @RequestParam("userId") String userId) {
        //TODO: 这里暂时用了UserInfoEntity作为数据的承载，等待数据库有baseInfo对应的视图时应该更改，并且给予对应的UserBaseInfo的构造函数。
        try {
            UserInfoEntity userInfoEntity = userService.findUserInfoByUserId(Long.parseLong(userId));

            UserBaseInfoResponse userBaseInfoResponse = new UserBaseInfoResponse();

            userBaseInfoResponse.setAvatar(userInfoEntity.getAvatarUrl());
            userBaseInfoResponse.setNickname(userInfoEntity.getNickName());
            userBaseInfoResponse.setLevel(1);
            userBaseInfoResponse.setCodeAge(1);
            userBaseInfoResponse.setBlogNum(0);
            userBaseInfoResponse.setFollowNum(0);
            userBaseInfoResponse.setFanNum(0);

            return new MyResponseEntity<>(Code.OK, "成功查找到本用户信息", userBaseInfoResponse);
        } catch (NullPointerException e) {
            return new MyResponseEntity<>(Code.BAD_OPERATION, "用户不存在", null);
        }
    }

    @GetMapping(path = "/user/subscribe")
    public MyResponseEntity<UserSubscribeStatusResponse> getUserSubscribe(@RequestParam("userId") String userId,
                                                                          @RequestParam("bloggerId") String bloggerId) {
        try {
            if (bloggerId == null) {
                return new MyResponseEntity<>(Code.BAD_OPERATION, "bloggerId为空", null);
            }
            Long uId = Long.parseLong(userId);
            Long bId = Long.parseLong(bloggerId);
            UserSubscribeStatusResponse userSubscribeStatusResponse = new UserSubscribeStatusResponse();
            userSubscribeStatusResponse = userService.findUserSubscribeStatusResponse(uId, bId);
            return new MyResponseEntity<>(Code.OK, "OK", userSubscribeStatusResponse);
        }
        catch(Exception e){
            return new MyResponseEntity<>(Code.BAD_OPERATION, "未知错误", null);
        }

    }

}
