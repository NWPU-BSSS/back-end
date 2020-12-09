package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.FollowEntity;
import com.nwpu.bsss.repository.FollowRepository;
import com.nwpu.bsss.service.FollowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class FollowServiceImpl implements FollowService {

    @Resource
    private FollowRepository followRepository;


    @Override
    @Transactional
    public long addFollow(long bloggerId, long userId) {
        FollowEntity followEntity = new FollowEntity(bloggerId, userId);
        return followRepository.save(followEntity).getId();
    }

    @Override
    public void deleteFollow(long bloggerId, long userId) {
        FollowEntity followEntity = followRepository.findByBloggerIdAndUserId(bloggerId, userId);
        followRepository.delete(followEntity);
    }

    @Override
    public boolean isFollowed(long bloggerId, long userId) {
        FollowEntity followEntity = followRepository.findByBloggerIdAndUserId(bloggerId,userId);
        return followEntity != null;
    }
}
