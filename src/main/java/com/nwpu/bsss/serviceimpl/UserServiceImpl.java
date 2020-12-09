package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.FollowEntity;
import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.repository.FollowRepository;
import com.nwpu.bsss.repository.UserInfoRepository;
import com.nwpu.bsss.repository.UserRepository;
import com.nwpu.bsss.response.UserSubscribeStatusResponse;
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

    @Resource
    private FollowRepository followRepository;

    @Override
    @Transactional
    public long createUser(UserEntity userEntity) {
        return userRepository.save(userEntity).getId();
    }

    @Override
    public void deleteUser(UserEntity userEntity) {
        //TODO:这里临时做了一个删除行的操作，后期需要使用时需要考虑各方面安全问题
        userRepository.delete(userEntity);
    }

    @Override
    public long createUserInfo(UserInfoEntity userInfoEntity) {
        return userInfoRepository.save(userInfoEntity).getId();
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

    @Override
    public UserSubscribeStatusResponse findUserSubscribeStatusResponse(Long userId,Long bloggerId){
        FollowEntity follow = new FollowEntity();
        UserSubscribeStatusResponse userSubscribeStatusResponse = new UserSubscribeStatusResponse();
//        follow = followRepository.getFollow(userId,bloggerId);
        if(follow==null){
            userSubscribeStatusResponse.setStatus(false);
        }
        else{
            userSubscribeStatusResponse.setStatus(true);
        }
        return userSubscribeStatusResponse;
    }

}
