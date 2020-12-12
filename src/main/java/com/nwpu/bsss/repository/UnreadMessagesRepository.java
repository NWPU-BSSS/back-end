package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.UnreadMessagesEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UnreadMessagesRepository extends JpaRepository<UnreadMessagesEntity, Long> {

    @Query(nativeQuery = true, value = "select * from UnreadMessages where UserId = ?1")
    UnreadMessagesEntity findByUserId(long userId);

    @Query(nativeQuery = true, value = "select * from UnreadMessages where Id = ?1")
    UnreadMessagesEntity findById(long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into UnreadMessages (UserId, Announcement, Follow, Message, Reply, Notice, `Like`, Comment) " +
            "values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)")
    int createUnreadMessage(long userId, int announcement, int follow, int message, int reply, int notice, int like, int comment);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from UnreadMessages where UserId = ?1")
    int deleteUnreadMessage(long userId);
}
