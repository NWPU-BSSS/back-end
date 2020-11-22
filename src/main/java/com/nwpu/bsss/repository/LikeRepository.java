package com.nwpu.bsss.repository;


import com.nwpu.bsss.domain.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface LikeRepository extends JpaRepository<CommentEntity, Long> {

    @Query(nativeQuery = true, value = "select count(Id) from Likes where BlogId=?1 ")
    long getBlogLikesNum(long blogId);


}
