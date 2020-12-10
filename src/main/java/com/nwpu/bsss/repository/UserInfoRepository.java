package com.nwpu.bsss.repository;


import com.nwpu.bsss.domain.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {
    //@Query(value = "SELECT * from UserInfos where Id=?1",nativeQuery = true)
    UserInfoEntity findUserInfoById(Long id);
}
