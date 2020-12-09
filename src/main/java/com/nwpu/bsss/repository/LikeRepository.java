package com.nwpu.bsss.repository;


import com.nwpu.bsss.domain.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    @Query(nativeQuery = true, value = "select count(Id) from Likes where BlogId=?1 ")
    long getBlogLikesNum(long blogId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from Likes where BlogId=?1 and UserId=?2 ")
    void cancelLike(long blogId, long UserId);

    @Query(nativeQuery = true, value = "select * from Likes where BlogId=?1 and userId=?2")
    LikeEntity findOne(long blogId,long userId);

}
