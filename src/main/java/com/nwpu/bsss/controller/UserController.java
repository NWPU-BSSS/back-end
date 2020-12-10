package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.domain.dto.SubscribeBloggerBody;
import com.nwpu.bsss.domain.dto.UpdateUserInfoBody;
import com.nwpu.bsss.response.*;
import com.nwpu.bsss.service.FollowService;
import com.nwpu.bsss.service.UserService;
import javassist.bytecode.analysis.MultiArrayType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author JerryChan
 * @date 2020-12-08 16:55:09
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private FollowService followService;

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
            if (StringUtils.isBlank(bloggerId)) {
                return new MyResponseEntity<>(Code.BAD_REQUEST, "bloggerId为空", null);
            }
            if(bloggerId.equals(userId)){
                return new MyResponseEntity<>(Code.BAD_OPERATION,"无效关注",null);
            }
            Long uId = Long.parseLong(userId);
            Long bId = Long.parseLong(bloggerId);
            UserSubscribeStatusResponse userSubscribeStatusResponse = new UserSubscribeStatusResponse();
            userSubscribeStatusResponse = userService.findUserSubscribeStatusResponse(uId, bId);
            return new MyResponseEntity<>(Code.OK, "OK", userSubscribeStatusResponse);
        }
        catch(NumberFormatException e){
            return new MyResponseEntity<>(Code.BAD_REQUEST, "参数类型错误", null);
        }

    }

    @PostMapping(path = "/user/subscribe")
    public MyResponseEntity<Object> subscribeBlogger(@RequestHeader("accessToken") String accessToken, @RequestParam("userId") String userId,
                                                     @RequestBody SubscribeBloggerBody subscribeBloggerBody){
        long uId = Long.parseLong(userId);
        boolean subscribe = subscribeBloggerBody.isSubscribe();
        long bloggerId = subscribeBloggerBody.getBloggerId();
        if(userService.findByUserID(bloggerId) == null)
            return new MyResponseEntity<>(Code.BAD_OPERATION, "博主不存在", null);
        if(bloggerId == uId)
            return new MyResponseEntity<>(Code.BAD_OPERATION, "不能关注自己", null);
        if(subscribe){
            if(followService.isFollowed(bloggerId, uId)){
                return new MyResponseEntity<>(Code.BAD_OPERATION, "已经关注该博主", null);
            }
            else{
                followService.addFollow(bloggerId, uId);
                return new MyResponseEntity<>(Code.OK, "ok", null);
            }
        }
        else {
            if(followService.isFollowed(bloggerId, uId)){
                followService.deleteFollow(bloggerId, uId);
                return new MyResponseEntity<>(Code.OK, "ok", null);
            }
            else {
                return new MyResponseEntity<>(Code.BAD_OPERATION, "已经取关该博主", null);
            }
        }
    }


    /**
     * 周亚旗
     */
    @GetMapping("/user/info")
    public MyResponseEntity<UserInfoResponse> getUserInfo(@RequestParam("userId") String userId){
        //TODO:university and academy set as default values
        if(StringUtils.isBlank(userId)){
            return new MyResponseEntity<>(Code.BAD_REQUEST,"用户id为空值",null);
        }
        Long userIdLong=Long.valueOf(userId);
        UserEntity userEntity=userService.findByUserID(userIdLong);
        UserInfoEntity userInfoEntity=userService.findUserInfoByUserId(userIdLong);

        if(userEntity==null || userInfoEntity==null){
            return new MyResponseEntity<>(Code.BAD_OPERATION,"用户不存在",null);
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
        long enrollTime=userInfoEntity.getStudentNo()/1000000L;
        userInfoResponse.setGraduateTime(String.valueOf(enrollTime+4));
        long codeAgeTime=new Date().getTime()-userEntity.getTime().getTime();
        userInfoResponse.setCodeAge(codeAgeTime/31536000000L);
        userInfoResponse.setLevel(userInfoEntity.getLevel());
        userInfoResponse.setAvatar(userInfoEntity.getAvatarUrl());

        return new MyResponseEntity<UserInfoResponse>(Code.OK,"ok",userInfoResponse);
    }

    /**
     *
     * @author JiangZhe
     */
    @PostMapping("/user/info")
    public MyResponseEntity updateUserInfo(@RequestHeader("accessToken") String accessToken,
                                           @RequestParam("userId") String userId,
                                           @RequestBody UpdateUserInfoBody updateUserInfoBody){

        Long id = Long.parseLong(userId);

        UserEntity userEntity = userService.findByUserID(id);
        UserInfoEntity userInfoEntity = userService.findUserInfoByUserId(id);

        if (userEntity == null || userInfoEntity == null){
            return new MyResponseEntity(Code.BAD_OPERATION, "用户不存在", null);
        }

        String newUserName = updateUserInfoBody.getUserName();
        String currUserName = userEntity.getUserName();
        if (newUserName.equals(currUserName)){
            return new MyResponseEntity(Code.BAD_REQUEST, "用户名与之前相同", null);
        }
        if (userService.findByUsername(newUserName) != null){
            return new MyResponseEntity(Code.BAD_REQUEST, "用户名已存在", null);
        }

        String gender = updateUserInfoBody.getGender();
        if (!gender.equals("1") && !gender.equals("0") && !gender.equals("2")){
            return new MyResponseEntity(Code.BAD_REQUEST, "性别有误：0为男，1为女，2为未知", null);
        }

        userEntity.setUserName(updateUserInfoBody.getUserName());
        userService.updateUserEntity(userEntity);

        userInfoEntity.setNickName(updateUserInfoBody.getNickName());
        userInfoEntity.setIntroduction(updateUserInfoBody.getIntroduction());
        userInfoEntity.setRealName(updateUserInfoBody.getRealName());
        userInfoEntity.setGender(Integer.parseInt(updateUserInfoBody.getGender()));
        userInfoEntity.setClassName(updateUserInfoBody.getClassName());
        userService.updateUserInfoEntity(userInfoEntity);

        return new MyResponseEntity(Code.OK, "OK", null);
    }
}
