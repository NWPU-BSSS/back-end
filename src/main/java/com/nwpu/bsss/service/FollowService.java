package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.FollowEntity;

public interface FollowService {
    long addFollow(long bloggerId, long userId);

    void deleteFollow(long bloggerId, long userId);

    //获取用户对博主的关注状态
    boolean isFollowed(long bloggerId, long userId);
}
