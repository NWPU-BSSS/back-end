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

    @GetMapping("/user/info")
    public MyResponseEntity<UserInfoResponse> getUserInfo(@RequestParam("userId") Long userId){
        //TODO:university and academy set as default values
        if(userId==null){
            return new MyResponseEntity<>(Code.BAD_REQUEST,"用户id为空值",null);
        }
        UserEntity userEntity=userService.findByUserID(userId);
        UserInfoEntity userInfoEntity=userService.findUserInfoByUserId(userId);

        if(userEntity==null || userInfoEntity==null){
            return new MyResponseEntity<>(Code.BAD_REQUEST,"未查询到该id对应的用户",null);
        }

        UserInfoResponse userInfoResponse=new UserInfoResponse();
        userInfoResponse.setUsername(userEntity.getUserName());
        userInfoResponse.setNickname(userInfoEntity.getNickName());
        userInfoResponse.setIntroduction(userInfoEntity.getIntroduction());
        userInfoResponse.setRealName(userInfoEntity.getRealName());
        userInfoResponse.setGender(userInfoEntity.getGender());
        userInfoResponse.setUniversity("西北工业大学");
        userInfoResponse.setAcademy("软件学院");
        userInfoResponse.setClassName(userInfoEntity.getClassName());
        userInfoEntity.getStudentNo();
        long enrollTime=userInfoEntity.getStudentNo()/1000000L;
        userInfoResponse.setGraduateTime(String.valueOf(enrollTime+4));
        long codeAgeTime=new Date().getTime()-userEntity.getTime().getTime();
        userInfoResponse.setCodeAge(codeAgeTime/31536000000L);
        userInfoResponse.setLevel(userInfoEntity.getLevel());
        userInfoResponse.setAvatar(userInfoEntity.getAvatarUrl());

        return new MyResponseEntity<UserInfoResponse>(Code.OK,"ok",userInfoResponse);
    }
}
