package com.nwpu.bsss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nwpu.bsss.domain.UserEntity;

/**
 * 相应JavaBean的持久层
 */
public interface UserRepository extends JpaRepository<UserEntity,Long> {

//    @Query(value = "SELECT * FROM BSSS_Dev.User WHERE Email=?1", nativeQuery = true)
    UserEntity findByEmail(String email);

    UserEntity findUserById(long id);

    UserEntity findUserByUserName(String userName);

}
