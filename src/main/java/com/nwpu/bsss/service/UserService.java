package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.domain.UserInfoEntity;

public interface UserService {

    long createUser(UserEntity userEntity);

    UserEntity findByUserID(long id);

    UserEntity findByUserEmail(String email);

    UserInfoEntity findUserInfoByUserId(long id);

}
