package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.FollowEntity;
import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.repository.*;
import com.nwpu.bsss.response.UserSubscribeStatusResponse;
import com.nwpu.bsss.response.UserSubscribesAndFansResponse;
import com.nwpu.bsss.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

@Service    //注入spring容器
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserInfoRepository userInfoRepository;

    @Resource
    private FollowRepository followRepository;

    @Resource
    private BlogRepository blogRepository;

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private FavoriteRepository favoriteRepository;

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
    @Transactional
    public void updateUserEntity(UserEntity userEntity){
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void updateUserInfoEntity(UserInfoEntity userInfoEntity){
        userInfoRepository.save(userInfoEntity);
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
        follow = followRepository.getFollow(userId,bloggerId);
        if(follow==null){
            userSubscribeStatusResponse.setStatus(false);
        }
        else{
            userSubscribeStatusResponse.setStatus(true);
        }
        return userSubscribeStatusResponse;
    }

    @Override
    public long getUserBlogNumByUserId(long id) {
        return blogRepository.findAllByAuthorId(id).size();
    }

    @Override
    public long getUserFanNumByUserId(long id) {
        return followRepository.findAllByBlogerId(id).size();
    }

    @Override
    public long getUserCommentNumByUserId(long id) {
        return commentRepository.findAllByUserId(id).size();
    }

    @Override
    public long getFavoriteNumByUserId(long id) {
        Set<BlogEntity> blogs = blogRepository.findAllByAuthorId(id);
        long num = 0L;
        for (BlogEntity entity : blogs){
            num += favoriteRepository.findAllByBlogId(entity.getId()).size();
        }
        return num;
    }

    @Override
    public UserSubscribesAndFansResponse findUsersubscrivesByUserId(Long userId){
        List<FollowEntity> followList = this.followRepository.findAllByUserId(userId);
        UserSubscribesAndFansResponse userSubscribesAndFansResponse = new UserSubscribesAndFansResponse();
        ArrayList<UserSubscribesAndFansResponse.SubscribeAndFansBody> subscribeBodies = new ArrayList<UserSubscribesAndFansResponse.SubscribeAndFansBody>();
        int count = followList.size();
        for(int i = 0;i < followList.size();i++){
            UserSubscribesAndFansResponse.SubscribeAndFansBody subscribeAndFansBody = new UserSubscribesAndFansResponse.SubscribeAndFansBody();
            FollowEntity follow = followList.get(i);
            Long bloggerId = follow.getBloggerId();
            UserInfoEntity userInfo = userInfoRepository.findUserInfoById(bloggerId);
            subscribeAndFansBody.setNickname(userInfo.getNickName());
            subscribeAndFansBody.setAvatar(userInfo.getAvatarUrl());
            subscribeAndFansBody.setBloggerId(bloggerId);
            subscribeAndFansBody.setIntroduction(userInfo.getIntroduction());
            subscribeAndFansBody.setIsSubscribed(true);
            subscribeBodies.add(subscribeAndFansBody);
        }
        userSubscribesAndFansResponse.setUserSubscribes(subscribeBodies);
        return userSubscribesAndFansResponse;
    }

    @Override
    public UserSubscribesAndFansResponse findBloggersubscrivesByUserId(Long userId, Long bloggerId){
        List<FollowEntity> followList = this.followRepository.findAllByUserId(bloggerId);
        UserSubscribesAndFansResponse userSubscribesAndFansResponse = new UserSubscribesAndFansResponse();
        ArrayList<UserSubscribesAndFansResponse.SubscribeAndFansBody> subscribeBodies = new ArrayList<UserSubscribesAndFansResponse.SubscribeAndFansBody>();
        int count = followList.size();
        for(int i = 0;i < followList.size();i++){
            UserSubscribesAndFansResponse.SubscribeAndFansBody subscribeAndFansBody = new UserSubscribesAndFansResponse.SubscribeAndFansBody();
            FollowEntity follow = followList.get(i);
            Long bId = follow.getBloggerId();
            UserInfoEntity userInfo = userInfoRepository.findUserInfoById(bId);
            subscribeAndFansBody.setNickname(userInfo.getNickName());
            subscribeAndFansBody.setAvatar(userInfo.getAvatarUrl());
            subscribeAndFansBody.setBloggerId(bId);
            subscribeAndFansBody.setIntroduction(userInfo.getIntroduction());
            if(bId == userId){
                subscribeAndFansBody.setIsSubscribed(false);
            }
            else if(followRepository.findByBloggerIdAndUserId(bId,userId)==null){
                subscribeAndFansBody.setIsSubscribed(false);
            }
            else {
                subscribeAndFansBody.setIsSubscribed(true);
            }
            subscribeBodies.add(subscribeAndFansBody);
        }
        userSubscribesAndFansResponse.setUserSubscribes(subscribeBodies);
        return userSubscribesAndFansResponse;
    }
    @Override
    public UserSubscribesAndFansResponse findBloggerFansByUserId(Long userId, Long bloggerId){
        List<FollowEntity> followList = this.followRepository.findAllByBloggerId(bloggerId);
        UserSubscribesAndFansResponse userSubscribesAndFansResponse = new UserSubscribesAndFansResponse();
        ArrayList<UserSubscribesAndFansResponse.SubscribeAndFansBody> subscribeBodies = new ArrayList<UserSubscribesAndFansResponse.SubscribeAndFansBody>();
        int count = followList.size();
        for(int i = 0;i < followList.size();i++){
            UserSubscribesAndFansResponse.SubscribeAndFansBody subscribeAndFansBody = new UserSubscribesAndFansResponse.SubscribeAndFansBody();
            FollowEntity follow = followList.get(i);
            Long uId = follow.getUserId();
            UserInfoEntity userInfo = userInfoRepository.findUserInfoById(uId);
            subscribeAndFansBody.setNickname(userInfo.getNickName());
            subscribeAndFansBody.setAvatar(userInfo.getAvatarUrl());
            subscribeAndFansBody.setBloggerId(uId);
            subscribeAndFansBody.setIntroduction(userInfo.getIntroduction());
            if(uId == userId){
                subscribeAndFansBody.setIsSubscribed(false);
            }
            else if(followRepository.findByBloggerIdAndUserId(uId,userId)==null){
                subscribeAndFansBody.setIsSubscribed(false);
            }
            else {
                subscribeAndFansBody.setIsSubscribed(true);
            }
            subscribeBodies.add(subscribeAndFansBody);
        }
        userSubscribesAndFansResponse.setUserSubscribes(subscribeBodies);
        return userSubscribesAndFansResponse;
    }
}
