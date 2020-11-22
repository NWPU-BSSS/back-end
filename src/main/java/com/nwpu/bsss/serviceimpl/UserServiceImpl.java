package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.repository.UserInfoRepository;
import com.nwpu.bsss.repository.UserRepository;
import com.nwpu.bsss.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service    //注入spring容器
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserInfoRepository userInfoRepository;

    @Override
    @Transactional
    public long createUser(UserEntity userEntity) {
        return userRepository.save(userEntity).getId();
    }

    @Override
    public UserEntity findByUserID(long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public UserEntity findByUserEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserInfoEntity findUserInfoByUserId(Long id) {return userInfoRepository.findUserInfoById(id);}

    public UserEntity findByUsername(String username) {
        return userRepository.findUserByUserName(username);
    }

}
