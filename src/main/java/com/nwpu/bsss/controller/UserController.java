package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.BrowseEntity;
import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.domain.dto.JsonAvatarResponse;
import com.nwpu.bsss.domain.dto.BrowseBlogsBody;
import com.nwpu.bsss.domain.dto.SubscribeBloggerBody;
import com.nwpu.bsss.domain.dto.UpdateUserInfoBody;
import com.nwpu.bsss.response.*;
import com.nwpu.bsss.service.FollowService;
import com.nwpu.bsss.service.UnreadMessagesService;
import com.nwpu.bsss.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author JerryChan
 * @date 2020-12-08 16:55:09
 */
@Slf4j
@RestController
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private FollowService followService;
    @Resource
    private UnreadMessagesService unreadMessagesService;
    @Value("${serverURL}")
    private String serverURL;

    @GetMapping("/baseInfo")
    public MyResponseEntity<UserBaseInfoResponse> getUserBaseInfo(@RequestHeader("accessToken") String accessToken,
                                                                  @RequestParam("userId") String userId) {
        //TODO: 这里暂时用了UserInfoEntity作为数据的承载，等待数据库有baseInfo对应的视图时应该更改，并且给予对应的UserBaseInfo的构造函数。
        try {
            UserInfoEntity userInfoEntity = userService.findUserInfoByUserId(Long.parseLong(userId));

            UserBaseInfoResponse userBaseInfoResponse = new UserBaseInfoResponse();

            userBaseInfoResponse.setAvatar(serverURL+userInfoEntity.getAvatarUrl());
            userBaseInfoResponse.setNickname(userInfoEntity.getNickName());
            userBaseInfoResponse.setLevel(1);
            userBaseInfoResponse.setCodeAge(1);
            userBaseInfoResponse.setBlogNum(0);
            userBaseInfoResponse.setFollowNum(0);
            userBaseInfoResponse.setFanNum(0);

            return new MyResponseEntity<>(Code.OK, "ok", userBaseInfoResponse);
        } catch (NullPointerException e) {
            return new MyResponseEntity<>(Code.BAD_OPERATION, "User not exist", null);
        }
    }

    @GetMapping(path = "/user/subscribe")
    public MyResponseEntity<UserSubscribeStatusResponse> getUserSubscribe(@RequestParam("userId") String userId,
                                                                          @RequestParam("bloggerId") String bloggerId) {
        try {
            if (StringUtils.isBlank(bloggerId)) {
                return new MyResponseEntity<>(Code.BAD_REQUEST, "Invalid bloggerId", null);
            }
            Long uId = Long.parseLong(userId);
            Long bId = Long.parseLong(bloggerId);
            UserSubscribeStatusResponse userSubscribeStatusResponse = new UserSubscribeStatusResponse();
            if(bloggerId == userId){
                userSubscribeStatusResponse.setStatus(false);
                return new MyResponseEntity<>(Code.OK,"ok",userSubscribeStatusResponse);
            }
            userSubscribeStatusResponse = userService.findUserSubscribeStatusResponse(uId, bId);
            return new MyResponseEntity<>(Code.OK, "ok", userSubscribeStatusResponse);
        }
        catch(NumberFormatException e){
            return new MyResponseEntity<>(Code.BAD_REQUEST, "Invalid param", null);
        }

    }

    @PostMapping(path = "/user/subscribe")
    public MyResponseEntity<Object> subscribeBlogger(@RequestHeader("accessToken") String accessToken, @RequestParam("userId") String userId,
                                                     @RequestBody SubscribeBloggerBody subscribeBloggerBody){
        long uId = Long.parseLong(userId);
        boolean subscribe = subscribeBloggerBody.isSubscribe();
        long bloggerId = subscribeBloggerBody.getBloggerId();
        if(userService.findByUserID(bloggerId) == null)
            return new MyResponseEntity<>(Code.BAD_OPERATION, "Blogger not exist", null);
        if(bloggerId == uId)
            return new MyResponseEntity<>(Code.BAD_OPERATION, "Cannot subscribe myself", null);
        if(subscribe){
            if(followService.isFollowed(bloggerId, uId)){
                return new MyResponseEntity<>(Code.BAD_OPERATION, "Already subscribed", null);
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
                return new MyResponseEntity<>(Code.BAD_OPERATION, "Already unsubscribed", null);
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
            return new MyResponseEntity<>(Code.BAD_REQUEST,"Invalid userId",null);
        }
        Long userIdLong=Long.valueOf(userId);
        UserEntity userEntity=userService.findByUserID(userIdLong);
        UserInfoEntity userInfoEntity=userService.findUserInfoByUserId(userIdLong);

        if(userEntity==null || userInfoEntity==null){
            return new MyResponseEntity<>(Code.BAD_OPERATION,"User not exist",null);
        }

        UserInfoResponse userInfoResponse=new UserInfoResponse();
        userInfoResponse.setUsername(userEntity.getUserName());
        userInfoResponse.setNickname(userInfoEntity.getNickName());
        userInfoResponse.setIntroduction(userInfoEntity.getIntroduction());
        userInfoResponse.setRealName(userInfoEntity.getRealName());
        userInfoResponse.setGender(userInfoEntity.getGender());
        userInfoResponse.setUniversity("Northwestern Polytechnical University");
        userInfoResponse.setAcademy("School of Software");
        userInfoResponse.setClassName(userInfoEntity.getClassName());
        long enrollTime=userInfoEntity.getStudentNo()/1000000L;
        userInfoResponse.setGraduateTime(String.valueOf(enrollTime+4));
        long codeAgeTime=new Date().getTime()-userEntity.getTime().getTime();
        userInfoResponse.setCodeAge(codeAgeTime/31536000000L);
        userInfoResponse.setLevel(userInfoEntity.getLevel());
        userInfoResponse.setAvatar(serverURL+userInfoEntity.getAvatarUrl());

        return new MyResponseEntity<>(Code.OK,"ok",userInfoResponse);
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
            return new MyResponseEntity(Code.BAD_OPERATION, "User not exist", null);
        }

        String newUserName = updateUserInfoBody.getUserName();

        if (StringUtils.isBlank(newUserName)){
            return new MyResponseEntity(Code.BAD_REQUEST, "Invalid username", null);
        }
        if (userService.findByUsername(newUserName) != null){
            UserEntity AnotherUser = userService.findByUsername(newUserName);
            if (!userEntity.equals(AnotherUser)){
                return new MyResponseEntity(Code.BAD_OPERATION, "username is already used", null);
            }
        }

        String introduction = updateUserInfoBody.getIntroduction();
        if (StringUtils.isBlank(introduction)){
            return new MyResponseEntity(Code.BAD_REQUEST, "Invalid introduction", null);
        }

        String gender = updateUserInfoBody.getGender();
        if (!gender.equals("1") && !gender.equals("0") && !gender.equals("2")){
            return new MyResponseEntity(Code.BAD_REQUEST, "Invalid gender：0:male，1:female，2:unknown", null);
        }

        userEntity.setUserName(updateUserInfoBody.getUserName());
        userService.updateUserEntity(userEntity);

        userInfoEntity.setNickName(updateUserInfoBody.getNickname());
        userInfoEntity.setIntroduction(updateUserInfoBody.getIntroduction());
        userInfoEntity.setRealName(updateUserInfoBody.getRealName());
        userInfoEntity.setGender(Integer.parseInt(updateUserInfoBody.getGender()));
        userInfoEntity.setClassName(updateUserInfoBody.getClassName());
        userService.updateUserInfoEntity(userInfoEntity);

        return new MyResponseEntity(Code.OK, "ok", null);
    }
    /**
     * @author alecHe
     *
     */
    @PostMapping("user/avatar")
    public MyResponseEntity<Object> setUserAvatar(@Param("userId") String userId,
                                            @RequestHeader("accessToken")String accessToken,
                                            @RequestParam("file") MultipartFile file){
        List<String> format = Arrays.asList(".jpg",".jpeg",".png");
        String prefix;
        String url = serverURL;
        try {

            int lastIndexOf = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");

            String suffix = file.getOriginalFilename().substring(lastIndexOf);
            log.info(suffix);
            if (!format.contains(suffix)){
                return new MyResponseEntity<>(Code.BAD_OPERATION,"File format not support",null);
            }
            url += userService.setUserAvatar(file,Long.parseLong(userId));
        }catch (Exception e){
            log.error("上传失败");
            return new MyResponseEntity<>(Code.BAD_OPERATION,"File upload fail",null);
        }
        log.info("上传成功");
        return MyResponseEntity.sendOK(new JsonAvatarResponse(url));

    }

    @GetMapping(path = "user/subscribes" )
    public MyResponseEntity<ArrayList<UserSubscribesAndFansResponse.SubscribeAndFansBody>> getUserSubscribes(@RequestParam("userId") String userId,
                                                                             @RequestParam("bloggerId") String bloggerId){

        if (StringUtils.isBlank(bloggerId)) {
            return new MyResponseEntity<>(Code.BAD_REQUEST, "Invalid bloggerId", null);
        }
        try{
            long uId = Long.parseLong(userId);
            long bId = Long.parseLong(bloggerId);

            if (userService.findByUserID(uId)==null){
                return new MyResponseEntity<>(Code.BAD_REQUEST,"User not exist",null);
            }
            if (userService.findByUserID(bId)==null){
                return new MyResponseEntity<>(Code.BAD_REQUEST,"Invalid BloggerId",null);
            }
            if(uId == bId){
                UserSubscribesAndFansResponse userSubscribesAndFansResponse = new UserSubscribesAndFansResponse();
                userSubscribesAndFansResponse = userService.findUsersubscrivesByUserId(uId);
                return new MyResponseEntity<>(Code.OK, "ok", userSubscribesAndFansResponse.getUserSubscribesAndFans());
            }
            else{
                UserSubscribesAndFansResponse userSubscribesAndFansResponse = new UserSubscribesAndFansResponse();
                userSubscribesAndFansResponse = userService.findBloggersubscrivesByUserId(uId,bId);
                return new MyResponseEntity<>(Code.OK,"ok", userSubscribesAndFansResponse.getUserSubscribesAndFans());
            }
            }
        catch(NumberFormatException e){
            return new MyResponseEntity<>(Code.BAD_REQUEST,"Invalid param",null);
        }
    }
        @GetMapping(path = "user/fans" )
        public MyResponseEntity<ArrayList<UserSubscribesAndFansResponse.SubscribeAndFansBody>> getBloggerfans(@RequestParam("userId") String userId,
                                                                                                              @RequestParam("bloggerId") String bloggerId) {

            if (StringUtils.isBlank(bloggerId)) {
                return new MyResponseEntity<>(Code.BAD_REQUEST, "Invalid bloggerId", null);
            }
            try {
                long uId = Long.parseLong(userId);
                long bId = Long.parseLong(bloggerId);

                if (userService.findByUserID(uId) == null) {
                    return new MyResponseEntity<>(Code.BAD_REQUEST, "User not exist", null);
                }
                if (userService.findByUserID(bId) == null) {
                    return new MyResponseEntity<>(Code.BAD_REQUEST, "Invalid bloggerId", null);
                }
                    UserSubscribesAndFansResponse userSubscribesAndFansResponse = new UserSubscribesAndFansResponse();
                    userSubscribesAndFansResponse = userService.findBloggerFansByUserId(uId,bId);
                    return new MyResponseEntity<>(Code.OK, "ok", userSubscribesAndFansResponse.getUserSubscribesAndFans());

            } catch (NumberFormatException e) {
                return new MyResponseEntity<>(Code.BAD_REQUEST, "Invalid param", null);
            }
    }

    /**
     * @author JiangZhe
     */
    @PostMapping("/user/browse/blogs")
    public MyResponseEntity getUserBrowseBlogs(@RequestHeader("accessToken") String accessToken,
                                                                @RequestParam("userId") String userId){
        Long id = Long.parseLong(userId);

        UserEntity userEntity = userService.findByUserID(id);

        if (userEntity == null){
            return new MyResponseEntity<>(Code.BAD_OPERATION, "User not exist", null);
        }

        List<BrowseEntity> browseList = userService.findBrowseBlogsByUserId(id);

        List<BrowseBlogsBody> blogList = new ArrayList<>();

        Iterator iterator = browseList.iterator();
        while(iterator.hasNext()){
            BrowseEntity browseEntity = (BrowseEntity) iterator.next();
            long blogId = browseEntity.getBlogId();
            BlogEntity blogEntity = userService.findByBlogId(blogId);

            blogList.add(new BrowseBlogsBody(blogEntity.getId(),blogEntity.getTitle()));
        }
        return new MyResponseEntity(Code.OK, "ok", blogList);

    }
}
