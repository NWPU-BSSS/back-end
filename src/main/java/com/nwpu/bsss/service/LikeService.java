package com.nwpu.bsss.service;

public interface LikeService {

    long getLikesNum(long blogId);

    void likeBlog(long blogId, long userId, boolean like);

    boolean getLikeStatus(long blogId,long userId);
}
