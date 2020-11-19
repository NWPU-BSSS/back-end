package com.nwpu.bsss.repository;


import com.nwpu.bsss.domain.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {
    UserInfoEntity findUserInfoById(long id);
}
