package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.domain.UserInfoEntity;

import java.util.Optional;

public interface UserService {

    long createUser(UserEntity userEntity);

    UserEntity findByUserID(long id);

    UserEntity findByUserEmail(String email);

    UserInfoEntity findUserInfoByUserId(Long id);
    
    UserEntity findByUsername(String username);

}
