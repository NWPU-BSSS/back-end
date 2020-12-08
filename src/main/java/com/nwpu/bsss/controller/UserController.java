package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.domain.dto.LoginUserBody;
import com.nwpu.bsss.domain.dto.RegisterBody;
import com.nwpu.bsss.exceptions.ValidationException;
import com.nwpu.bsss.response.*;
import com.nwpu.bsss.service.UserService;
import com.nwpu.bsss.utils.UserInfoValidator;
import com.nwpu.bsss.utils.VerifyClient;
import org.apache.http.impl.client.cache.memcached.SHA256KeyHashingScheme;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {

    public static Map<String, Long> token2Id = new HashMap<>();

    @Resource
    private UserService userService;
    @Resource
    private VerifyClient verifyClient;

    @PostMapping(path = "/usernameCheck")
    public MyResponseEntity<UsernameCheckResponse> checkUsername(@RequestBody RegisterBody registerBody) {

        UserEntity userEntity = userService.findByUsername(registerBody.getUsername());
        if (userEntity == null) {
            return new MyResponseEntity<>(Code.OK, "用户名未被占用", new UsernameCheckResponse(false));
        }
        return new MyResponseEntity<>(Code.OK, "用户名已被占用", new UsernameCheckResponse(true));
    }

    @PostMapping(path = "/emailCheck")
    public MyResponseEntity<UsernameCheckResponse> checkEmail(@RequestBody RegisterBody registerBody) {

        UserEntity userEntity = userService.findByUserEmail(registerBody.getEmail());
        if (userEntity == null) {
            return new MyResponseEntity<>(Code.OK, "邮箱未被占用", new UsernameCheckResponse(false));
        }
        return new MyResponseEntity<>(Code.OK, "邮箱已被占用", new UsernameCheckResponse(true));
    }

    @PostMapping(path = "/register/verifyCode")
    public MyResponseEntity<RegisterResponse> sendVerifyCode(@RequestBody RegisterBody registerBody) {

        //请求验证服务器发送验证码，true表示验证服务器发送成功
        String result = verifyClient.sentVerifyCode(registerBody.getEmail());

        if (result.equals("true")) {
            return new MyResponseEntity<>(Code.OK, "验证码发送成功", null);
        } else {    //false
            return new MyResponseEntity<>(Code.BAD_OPERATION, "验证码发送失败", null);
        }
    }

    @PostMapping(path = "/register")
    public MyResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterBody registerBody, BindingResult bindingResult) {

        //一个是用户实体，一个是用户扩展信息实体
        UserEntity user = new UserEntity(registerBody);
        UserInfoEntity userInfo = new UserInfoEntity();

        //validate email format
        new UserInfoValidator().validate(user, bindingResult);

        //the exception throw here will be taken over by the 'advice' layer
        if (bindingResult.hasErrors()) {
            throw new ValidationException("邮箱格式错误");
        }

        //请求验证服务器验证邮箱和验证码
        String result = verifyClient.verifyEmail(registerBody.getEmail(), String.valueOf(registerBody.getVerifyCode()));

        if (result.equals("false")) {
            return new MyResponseEntity<>(Code.BAD_OPERATION, "邮箱验证失败", null);
        } else {    //true
            try {
                //创建用户
                long userId = userService.createUser(user);
                //外键设置
                userInfo.setId(userId);
                try {
                    //创建用户扩展信息
                    userService.createUserInfo(userInfo);
                    return new MyResponseEntity<>(Code.OK, "注册成功", null);
                } catch (Exception e) {
                    e.printStackTrace();
                    //如果UserInfo创建失败，同时删除刚创建的新user。
                    userService.deleteUser(user);
                    return new MyResponseEntity<>(Code.BAD_OPERATION, "未知错误", null);
                }
            } catch (DataIntegrityViolationException e) {
                return new MyResponseEntity<>(Code.BAD_OPERATION, "该邮箱已被占用，请重试", null);
            }
        }
    }

    @PostMapping(path = "/login")
    public MyResponseEntity<UserLoginResponse> login(@RequestBody LoginUserBody loginUserBody) {

        UserEntity userEntity;

        if (loginUserBody.getEmail() != null && !loginUserBody.getEmail().equals("")) {
            //邮箱登录
            userEntity = userService.findByUserEmail(loginUserBody.getEmail());
        } else {
            //用户名登录
            userEntity = userService.findByUsername(loginUserBody.getUsername());
        }

        String password;
        try {
            password = userEntity.getPassword();
        } catch (NullPointerException e) {
            return new MyResponseEntity<>(Code.BAD_OPERATION, "用户不存在", null);
        }

        if (password.equals(loginUserBody.getPassword())) {
            //登录成功，生成token
            String rawToken = userEntity.getId() + "-" +
                    userEntity.getUserName() + "-" +
                    new Timestamp(new Date().getTime()).toString();

            String token = new SHA256KeyHashingScheme().hash(rawToken);

            //存储token
            UserController.token2Id.put(token, userEntity.getId());
            UserLoginResponse userLoginResponse = new UserLoginResponse();
            userLoginResponse.setAccessToken(token);

            return new MyResponseEntity<>(Code.OK, "登录成功", userLoginResponse);
        } else {
            //登录失败
            return new MyResponseEntity<>(Code.BAD_OPERATION, "密码错误", null);
        }
    }

    @GetMapping("/baseInfo")
    public MyResponseEntity<UserBaseInfoResponse> getUserBaseInfo(@RequestHeader("accessToken") String token) {
        //TODO: 这里暂时用了UserInfoEntity作为数据的承载，等待数据库有baseInfo对应的视图时应该更改，并且给予对应的UserBaseInfo的构造函数。
        try {
            Long id = token2Id.get(token);
            if (id == null) {
                return new MyResponseEntity<>(Code.BAD_OPERATION, "token无效", null);
            }

            UserInfoEntity userInfoEntity = userService.findUserInfoByUserId(id);

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
