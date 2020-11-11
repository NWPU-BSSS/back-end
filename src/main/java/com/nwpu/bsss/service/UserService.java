package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.UserEntity;

public interface UserService {

    long createUser(UserEntity userEntity);

    UserEntity findByUserID(long id);

    UserEntity findByUserEmail(String email);

}
