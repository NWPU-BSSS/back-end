package com.nwpu.bsss.controller;

import com.nwpu.bsss.domain.AccessTokensEntity;
import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.domain.dto.LoginUserBody;
import com.nwpu.bsss.domain.dto.RegisterBody;
import com.nwpu.bsss.exceptions.ValidationException;
import com.nwpu.bsss.response.*;
import com.nwpu.bsss.service.TokenCheckService;
import com.nwpu.bsss.service.UserService;
import com.nwpu.bsss.utils.UserInfoValidator;
import com.nwpu.bsss.utils.VerifyClient;
import org.apache.http.impl.client.cache.memcached.SHA256KeyHashingScheme;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author JerryChan
 * @desc 用于用户登录注册操作
 * @date 2020-12-08 18:17:33
 */
@RestController
public class IdentificationController {
    @Resource
    private UserService userService;
    @Resource
    private TokenCheckService tokenCheckService;
    @Resource
    private VerifyClient verifyClient;

    @PostMapping(path = "/usernameCheck")
    public MyResponseEntity<UsernameCheckResponse> checkUsername(@RequestBody RegisterBody registerBody) {

        UserEntity userEntity = userService.findByUsername(registerBody.getUsername());
        if (userEntity == null) {
            return new MyResponseEntity<>(Code.OK, "Username is useful", new UsernameCheckResponse(false));
        }
        return new MyResponseEntity<>(Code.OK, "Username is already used", new UsernameCheckResponse(true));
    }

    @PostMapping(path = "/emailCheck")
    public MyResponseEntity<UsernameCheckResponse> checkEmail(@RequestBody RegisterBody registerBody) {

        UserEntity userEntity = userService.findByUserEmail(registerBody.getEmail());
        if (userEntity == null) {
            return new MyResponseEntity<>(Code.OK, "Email is useful", new UsernameCheckResponse(false));
        }
        return new MyResponseEntity<>(Code.OK, "Email is already used", new UsernameCheckResponse(true));
    }

    @PostMapping(path = "/register/verifyCode")
    public MyResponseEntity<RegisterResponse> sendVerifyCode(@RequestBody RegisterBody registerBody) {

        //请求验证服务器发送验证码，true表示验证服务器发送成功
        String result = verifyClient.sentVerifyCode(registerBody.getEmail());

        if (result.equals("true")) {
            return new MyResponseEntity<>(Code.OK, "Send verification code success", null);
        } else {    //false
            return new MyResponseEntity<>(Code.BAD_OPERATION, "Send verification code fail", null);
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
            throw new ValidationException("Invalid email format");
        }

        //请求验证服务器验证邮箱和验证码
        String result = verifyClient.verifyEmail(registerBody.getEmail(), String.valueOf(registerBody.getVerifyCode()));

        if (result.equals("false")) {
            return new MyResponseEntity<>(Code.BAD_OPERATION, "Verification code check fail", null);
        } else {    //true
            try {
                //创建用户
                long userId = userService.createUser(user);
                //外键设置
                userInfo.setId(userId);
                try {
                    //创建用户扩展信息
                    userInfo.setNickName(user.getUserName());//默认nickname为用户名,其他均为空
                    userService.createUserInfo(userInfo);
                    return new MyResponseEntity<>(Code.OK, "Register success", null);
                } catch (Exception e) {
                    e.printStackTrace();
                    //如果UserInfo创建失败，同时删除刚创建的新user。
                    userService.deleteUser(user);
                    return new MyResponseEntity<>(Code.BAD_OPERATION, "Unknown error in server", null);
                }
            } catch (DataIntegrityViolationException e) {
                return new MyResponseEntity<>(Code.BAD_OPERATION, "Email or userName is already used", null);
            }
        }
    }

    /**
     * 用于处理用户登录事项，包括身份验证与token的存储等
     * @param loginUserBody 参数集合
     * @return Json格式的Token + userId
     */
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
            return new MyResponseEntity<>(Code.BAD_OPERATION, "User not exist", null);
        }

        if (password.equals(loginUserBody.getPassword())) {
            //登录成功
            AccessTokensEntity entity = tokenCheckService.findIdByUserId(userEntity.getId());
            String token;
            if(entity != null) {
                token = entity.getAccessToken();
            } else {
                //生成token
                String rawToken = userEntity.getId() + "-" +
                        userEntity.getUserName() + "-" +
                        new Timestamp(new Date().getTime()).toString();

                token = new SHA256KeyHashingScheme().hash(rawToken);
                //存储token
                entity = new AccessTokensEntity(token, userEntity.getId());
                tokenCheckService.insertToken(entity);
            }

            UserLoginResponse userLoginResponse = new UserLoginResponse();
            userLoginResponse.setAccessToken(token);
            userLoginResponse.setUserId(userEntity.getId());

            return new MyResponseEntity<>(Code.OK, "Login success", userLoginResponse);
        } else {
            //登录失败
            return new MyResponseEntity<>(Code.BAD_OPERATION, "Password error", null);
        }
    }
}
