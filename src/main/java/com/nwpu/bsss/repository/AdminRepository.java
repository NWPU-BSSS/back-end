package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.AdminsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author alecHe
 * @desc ...
 * @date 2020-12-07 11:36:43
 */
@Repository
public interface AdminRepository extends JpaRepository<AdminsEntity,Long> {
    /**
     * 通过用户名找admin
     * @param username 用户名
     * @return admin结果
     */
    Optional<AdminsEntity> findByUsername(String username);
}
