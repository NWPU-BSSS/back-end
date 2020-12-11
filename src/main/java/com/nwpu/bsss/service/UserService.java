package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.BrowseEntity;
import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.response.UserSubscribeStatusResponse;

import java.util.List;

public interface UserService {

    long createUser(UserEntity userEntity);

    void deleteUser(UserEntity userEntity);

    long createUserInfo(UserInfoEntity userInfoEntity);

    void updateUserEntity(UserEntity userEntity);

    void updateUserInfoEntity(UserInfoEntity userInfoEntity);

    List<BrowseEntity> findBrowseBlogsByUserId(long userId);

    BlogEntity findByBlogId(long blogId);

    UserEntity findByUserID(long id);

    UserEntity findByUserEmail(String email);

    UserInfoEntity findUserInfoByUserId(Long id);
    
    UserEntity findByUsername(String username);

    UserSubscribeStatusResponse findUserSubscribeStatusResponse(Long userId,Long bloggerId);

}
