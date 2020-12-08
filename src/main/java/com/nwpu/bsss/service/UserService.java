package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.response.UserSubscribeStatusResponse;

public interface UserService {

    long createUser(UserEntity userEntity);

    void deleteUser(UserEntity userEntity);

    long createUserInfo(UserInfoEntity userInfoEntity);

    UserEntity findByUserID(long id);

    UserEntity findByUserEmail(String email);

    UserInfoEntity findUserInfoByUserId(Long id);
    
    UserEntity findByUsername(String username);

    UserSubscribeStatusResponse findUserSubscribeStatusResponse(Long userId,Long bloggerId);

}
