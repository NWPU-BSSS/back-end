package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.FollowEntity;
import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    @Query(nativeQuery = true, value = "select * from Follow where UserId=?1 and BloggerId=?2")
    FollowEntity getFollow(Long userId, Long bloggerId) ;

    @Query(nativeQuery = true, value = "select * from Follow where BloggerId = ?1 and UserId = ?2")
    FollowEntity findByBloggerIdAndUserId(long bloggerId, long userId);

    @Query(nativeQuery = true, value = "select * from Follow where UserId =?1")
    List<FollowEntity> findAllByUserId(Long userId);

    @Query(nativeQuery = true, value = "select * from Follow where BloggerId =?1")
    List<FollowEntity> findAllByBloggerId(Long bloggerId);

    Set<FollowEntity> findAllByBlogerId(long bloggerId);
}
