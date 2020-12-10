package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.LikeEntity;
import com.nwpu.bsss.repository.LikeRepository;
import com.nwpu.bsss.service.LikeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

@Service
@Transactional
public class LikeServiceImpl implements LikeService {

    @Resource
    private LikeRepository likeRepository;

    @Override
    public long getLikesNum(long blogId) {
        return likeRepository.getBlogLikesNum(blogId);
    }

    @Override
    public void likeBlog(long blogId, long userId, boolean like) {
        //只在没点赞的情况下加入点赞表,防止重复存取
        if (like == true && likeRepository.findOne(blogId, userId) == null) {
            LikeEntity likeEntity = new LikeEntity(blogId, userId, new Timestamp(new Date().getTime()));
            likeRepository.save(likeEntity);
        }
        if (like == false)
            likeRepository.cancelLike(blogId, userId);
    }

    @Override
    public boolean getLikeStatus(long blogId, long userId) {
        LikeEntity likeEntity = likeRepository.findOne(blogId, userId);
        return likeEntity == null ? false : true;
    }

}
