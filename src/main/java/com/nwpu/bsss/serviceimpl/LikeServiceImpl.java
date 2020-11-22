package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.repository.LikeRepository;
import com.nwpu.bsss.service.LikeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LikeServiceImpl implements LikeService {

    @Resource
    private LikeRepository likeRepository;

    @Override
    public long getLikesNum(long blogId) {
        return likeRepository.getBlogLikesNum(blogId);
    }

}
